package com.example.medicalcareapp.event_manager

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed class AppEvents {
    object HideNoInternetScreen: AppEvents()
    object ShowNoInternetScreen: AppEvents()
    object TryAgain: AppEvents()
}

class EventManager(private val ioDispatcher: CoroutineDispatcher){
    private val _currentAppEvent = MutableSharedFlow<AppEvents>()
    val currentAppEvent = _currentAppEvent.asSharedFlow()

    fun emitEvent(event: AppEvents) {
        CoroutineScope(ioDispatcher).launch {
            _currentAppEvent.emit(event)
        }
    }
}