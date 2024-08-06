package com.example.domain.common

import com.example.domain.generic.GenericApiErrorModel

sealed interface NetworkResult<T>

class ApiSuccess<T>(val data: T) : NetworkResult<T>
class ApiError<T>(val networkCode: Int, val networkMessage: String?, val errorModel: GenericApiErrorModel? = null) :
    NetworkResult<T>

class ApiNoConnectivityError<T>(val message: String?) : NetworkResult<T>
class ApiException<T>(val e: Exception) : NetworkResult<T>
class ApiLoading<T> : NetworkResult<T>

fun <T, S> NetworkResult<T>.mapToType(mapping: (T) -> S): NetworkResult<S> {
    return when (this) {
        is ApiError -> ApiError(networkCode, networkMessage, errorModel)
        is ApiException -> ApiException(e)
        is ApiLoading -> ApiLoading()
        is ApiNoConnectivityError -> ApiNoConnectivityError(message)
        is ApiSuccess -> ApiSuccess(data = mapping(data))
    }
}

fun <T> NetworkResult<T>.getSuccessOrDefault(default: T?) = (this as? ApiSuccess)?.data ?: default
fun <T> NetworkResult<T>.getErrorOrNull(default: T?) = (this as? ApiError)
fun <T> NetworkResult<T>.getNoConnectivityErrorOrNull(default: T?) = (this as? ApiNoConnectivityError)
fun <T> NetworkResult<T>.getExceptionOrNull(default: T?) = (this as? ApiException)
fun <T> NetworkResult<T>.getLoadingOrNull(default: T?) = (this as? ApiLoading)
