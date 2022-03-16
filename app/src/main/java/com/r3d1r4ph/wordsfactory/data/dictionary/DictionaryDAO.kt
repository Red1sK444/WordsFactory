package com.r3d1r4ph.wordsfactory.data.dictionary

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryDAO {

    @GET("v2/entries/en/{word}")
    suspend fun getDictionary(
        @Path("word") word: String
    ): Response<List<DictionaryDAO>>
}