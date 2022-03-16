package com.r3d1r4ph.wordsfactory.data.dictionary

import com.r3d1r4ph.wordsfactory.data.Network

class DictionaryService {
    private val api = Network.retrofit.create(DictionaryDAO::class.java)

    suspend fun getDictionary(word: String) =
        api.getDictionary(word)
}