package com.example.medicalcareapp.screens.account_screen.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.usecases.account.UserAccountUseCase

class AccountViewModel(
    private val accountUseCase: UserAccountUseCase,
) : ViewModel() {

    fun logout() {
        accountUseCase.signOut()
    }
}