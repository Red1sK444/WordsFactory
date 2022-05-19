package com.r3d1r4ph.wordsfactory.data.utils

import com.r3d1r4ph.wordsfactory.domain.exceptions.UnknownException
import retrofit2.Response

inline fun <DTO, DOMAIN> safeApiCall(
    apiCall: () -> Response<DTO>,
    onSuccess: (Response<DTO>) -> DOMAIN
): DOMAIN {
    val response = apiCall.invoke()
    if (response.isSuccessful) {
        return onSuccess.invoke(response)
    }

    throw UnknownException()
}