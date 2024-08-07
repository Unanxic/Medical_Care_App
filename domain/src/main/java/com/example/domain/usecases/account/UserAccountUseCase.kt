package com.example.domain.usecases.account

import android.app.Activity
import com.example.domain.models.account.ContinuationFlow
import com.example.domain.models.account.UserAccountUseCaseEvents
import com.example.domain.repositories.account.FirebaseAccountRepository
import com.example.domain.utils.FlowLoading
import com.example.domain.utils.GenericFlowState
import com.example.domain.utils.setError
import com.example.domain.utils.setLoading
import com.example.domain.utils.setSuccess
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class UserAccountUseCase(private val activity: WeakReference<Activity>) :
    FirebaseAccountRepository {
    private val _authState =
        MutableStateFlow<GenericFlowState<FirebaseUser>>(FlowLoading())
    override val authState = _authState.asStateFlow()
    private val _accountUseCaseEvents: MutableStateFlow<UserAccountUseCaseEvents?> =
        MutableStateFlow(null)
    override val accountUseCaseEvents = _accountUseCaseEvents.asStateFlow()
    private var continuationFlow: ContinuationFlow? = null

    private lateinit var oneTapClient: SignInClient

    init {
        resetToInitialState()
        activity.get()?.let {
            oneTapClient = Identity.getSignInClient(it)
        }
    }

    override fun createWithEmailAndPassword(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) return
        _authState.setLoading()
        Firebase.auth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener { task ->
                handleFirebaseResponse(task)
            }
    }

    override fun signInWithEmailAndPassword(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) return
        _authState.setLoading()
        val credential = EmailAuthProvider
            .getCredential(username, password)

        if (_accountUseCaseEvents.value is UserAccountUseCaseEvents.ReAuthenticate) {
            reAuthenticate(credential)
        } else {
            Firebase.auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    handleFirebaseResponse(task)
                }
        }
    }


    override fun signOut() {
        oneTapClient.signOut().addOnCompleteListener {
            Firebase.auth.signOut()
            _authState.setSuccess(null)
            clearReAuthenticationFlow()
        }.addOnFailureListener {
            _authState.setError(it)
        }
    }

    override fun resetToInitialState() {
        _authState.setSuccess(Firebase.auth.currentUser)
    }

    override fun reloadAccount() {
        Firebase.auth.currentUser?.reload()?.addOnCompleteListener {
            resetToInitialState()
        }
    }

    private fun reAuthenticate(credential: AuthCredential) {
        val user = Firebase.auth.currentUser!!
        user.reauthenticate(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    emitEvent(
                        UserAccountUseCaseEvents.ReAuthenticatedDone(
                            successful = true,
                            continuationFlow = continuationFlow
                        )
                    )
                }
            }.addOnFailureListener {
                emitEvent(
                    UserAccountUseCaseEvents.ReAuthenticatedDone(
                        successful = false,
                        continuationFlow = continuationFlow
                    )
                )
            }
    }

    private fun emitEvent(event: UserAccountUseCaseEvents?) {
        CoroutineScope(Dispatchers.IO).launch {
            _accountUseCaseEvents.emit(event)
        }
    }

    override fun clearReAuthenticationFlow() {
        emitEvent(null)
        continuationFlow = null
    }

    private fun handleFirebaseResponse(task: Task<AuthResult>) {
        if (task.isSuccessful) {
            _authState.setSuccess(Firebase.auth.currentUser)
        } else {
            _authState.setError(task.exception)
        }
    }
}