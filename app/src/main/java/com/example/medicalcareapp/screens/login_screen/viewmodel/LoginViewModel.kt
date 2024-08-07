package com.example.medicalcareapp.screens.login_screen.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.usecases.account.UserAccountUseCase

class LoginViewModel(private val accountUseCase: UserAccountUseCase) : ViewModel() {
    val authState = accountUseCase.authState
    val accountUseCaseEvents = accountUseCase.accountUseCaseEvents

    fun loginWithMailAndPass(username: String, password: String) {
        accountUseCase.signInWithEmailAndPassword(username = username, password = password)
    }

    fun clearReAuthenticationFlow() {
        accountUseCase.clearReAuthenticationFlow()
    }

    fun clearError() {
        accountUseCase.resetToInitialState()
    }
}