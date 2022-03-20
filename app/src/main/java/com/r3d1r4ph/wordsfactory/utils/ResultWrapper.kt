package com.r3d1r4ph.wordsfactory.utils

import com.r3d1r4ph.wordsfactory.utils.exceptions.UnknownException
import retrofit2.Response

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Failure(val exception: Exception) : ResultWrapper<Nothing>()
}

suspend fun <DTO, DOMAIN> safeApiCall(
    apiCall: suspend () -> Response<DTO>,
    onSuccess: (Response<DTO>) -> ResultWrapper<DOMAIN>
): ResultWrapper<DOMAIN> {
    try {
        val response = apiCall.invoke()
        if (response.isSuccessful) {
            return onSuccess.invoke(response)
        }
    } catch (e: Exception) {
        return ResultWrapper.Failure(e)
    }
    return ResultWrapper.Failure(UnknownException())
}