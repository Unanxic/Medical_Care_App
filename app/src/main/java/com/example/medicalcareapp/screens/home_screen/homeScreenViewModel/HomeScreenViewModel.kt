package com.example.medicalcareapp.screens.home_screen.homeScreenViewModel

import androidx.lifecycle.ViewModel
import com.example.domain.usecases.account.UserAccountUseCase

class HomeScreenViewModel(
    private val accountUseCase: UserAccountUseCase,
) : ViewModel() {
    val accountState = accountUseCase.authState
}