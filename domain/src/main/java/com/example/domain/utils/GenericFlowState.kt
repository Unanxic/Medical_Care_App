package com.example.domain.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

sealed interface GenericFlowState<T>
class FlowLoading<T> : GenericFlowState<T>
class FlowError<T>(val ex: Exception?) : GenericFlowState<T>
class FlowSuccess<T>(val data: T?) : GenericFlowState<T>

fun <T> MutableStateFlow<GenericFlowState<T>>.setLoading() = CoroutineScope(Dispatchers.IO).launch {
    this@setLoading.emit(FlowLoading())
}

fun <T> MutableStateFlow<GenericFlowState<T>>.setSuccess(data: T?) =
    CoroutineScope(Dispatchers.IO).launch {
        this@setSuccess.emit(FlowSuccess(data))
    }

fun <T> MutableStateFlow<GenericFlowState<T>>.setError(ex: Exception? = null) =
    CoroutineScope(Dispatchers.IO).launch {
        this@setError.emit(FlowError(ex))
    }