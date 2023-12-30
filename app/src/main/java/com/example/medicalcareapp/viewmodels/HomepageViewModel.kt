package com.example.medicalcareapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


enum class CurrentHomeScreen {
    NONE,
    TREATMENT,
    CONTACTS,
    ACCOUNT,
}

class HomepageViewModel(private val ioDispatcher: CoroutineDispatcher): ViewModel() {
    private val _currentHomeScreen = MutableSharedFlow<CurrentHomeScreen>()
    val currentHomeScreen = _currentHomeScreen.asSharedFlow()

    fun emitCurrentScreen(screen: CurrentHomeScreen) {
        viewModelScope.launch(ioDispatcher) {
            _currentHomeScreen.emit(screen)
        }
    }
}