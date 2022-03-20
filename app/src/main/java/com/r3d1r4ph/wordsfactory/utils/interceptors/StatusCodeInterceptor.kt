package com.r3d1r4ph.wordsfactory.utils.interceptors

import com.r3d1r4ph.wordsfactory.utils.exceptions.NotFoundException
import com.r3d1r4ph.wordsfactory.utils.exceptions.StatusCodeException
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject

class StatusCodeInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        return try {
            handleResponse(chain.proceed(request))
        } catch (e: StatusCodeException) {
            throw e
        }
    }

    private fun handleResponse(response: Response): Response {
        when (response.code) {
            in 200..300 -> Unit
            404 -> throw NotFoundException()
            else -> throw StatusCodeException(JSONObject(response.body?.string()).getString("message"))
        }

        return response
    }
}