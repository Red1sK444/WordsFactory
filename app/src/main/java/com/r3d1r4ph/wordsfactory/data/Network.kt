package com.r3d1r4ph.wordsfactory.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.r3d1r4ph.wordsfactory.BuildConfig
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object Network {
    private const val BASE_URL = "https://api.dictionaryapi.dev/api/"
    private val mediaType = "application/json".toMediaType()

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        })
        .build()

    private val json = Json { ignoreUnknownKeys = true }

    @ExperimentalSerializationApi
    val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory(mediaType))
        .build()
}