package com.example.medicalcareapp.managers

import com.example.medicalcareapp.composables.top_bar.TopBarConfigs
import com.example.medicalcareapp.composables.top_bar.TopBarLayouts
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class CurrentHomeScreen {
    NONE,
    HISTORY,
    ALLERGIES,
    CONTACTS,
    ACCOUNT,
}

class HomeScreenManager(private val ioDispatcher: CoroutineDispatcher) {
    private val _currentHomeScreen = MutableSharedFlow<CurrentHomeScreen>(replay = 1)
    val currentHomeScreen = _currentHomeScreen.asSharedFlow()

    private val _lastSelectedScreen = MutableStateFlow<CurrentHomeScreen?>(null)
    val lastSelectedScreen = _lastSelectedScreen.asStateFlow()

    private val _topBarConfigs = MutableStateFlow(TopBarConfigs(topBarLayout = TopBarLayouts.MEDICAL_HISTORY_TEXT))
    val topBarConfigs = _topBarConfigs.asStateFlow()

    init {
        emitCurrentScreen(CurrentHomeScreen.HISTORY)
    }

    fun emitCurrentScreen(
        screen: CurrentHomeScreen,
        invokeOnCompletion: () -> Unit = {}
    ) {
        CoroutineScope(ioDispatcher).launch {
            _lastSelectedScreen.emit(currentHomeScreen.replayCache.lastOrNull())
            _currentHomeScreen.emit(screen)
        }.invokeOnCompletion {
            CoroutineScope(Dispatchers.Main).launch {
                invokeOnCompletion()
            }
        }
    }

    fun emitTopBarConfigs(topBarConfigs: TopBarConfigs) {
        CoroutineScope(ioDispatcher).launch {
            _topBarConfigs.emit(topBarConfigs)
        }
    }
}