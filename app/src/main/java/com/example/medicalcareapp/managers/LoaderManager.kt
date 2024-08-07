package com.example.medicalcareapp.managers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoaderManager {
    val loaderVisibility = MutableStateFlow(false)

    fun show() {
        CoroutineScope(Dispatchers.Default).launch {
            loaderVisibility.emit(true)
        }
    }

    fun hide() {
        CoroutineScope(Dispatchers.Default).launch {
            loaderVisibility.emit(false)
        }
    }
}