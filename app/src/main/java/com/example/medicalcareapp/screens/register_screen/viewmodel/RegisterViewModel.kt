package com.example.medicalcareapp.screens.register_screen.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.usecases.account.UserAccountUseCase

class RegisterViewModel(private val accountUseCase: UserAccountUseCase) : ViewModel() {
    val authState = accountUseCase.authState

    fun registerWithMailAndPass(username: String, password: String, confirmPassword: String) {
        accountUseCase.createWithEmailAndPassword(username = username, password = password)
    }
}