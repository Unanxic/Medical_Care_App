package com.example.domain.repositories.account

import com.example.domain.models.account.UserAccountUseCaseEvents
import com.example.domain.utils.GenericFlowState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.StateFlow

interface FirebaseAccountRepository {
    val authState: StateFlow<GenericFlowState<FirebaseUser>>
    val accountUseCaseEvents: StateFlow<UserAccountUseCaseEvents?>
    fun createWithEmailAndPassword(username: String, password: String)
    fun signInWithEmailAndPassword(username: String, password: String)
    fun signOut()
    fun clearReAuthenticationFlow()
    fun resetToInitialState()
    fun reloadAccount()
}