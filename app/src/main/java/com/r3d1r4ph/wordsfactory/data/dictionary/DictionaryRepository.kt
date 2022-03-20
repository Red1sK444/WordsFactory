package com.r3d1r4ph.wordsfactory.data.dictionary

import com.r3d1r4ph.wordsfactory.domain.Dictionary
import com.r3d1r4ph.wordsfactory.utils.ResultWrapper
import retrofit2.Response

interface DictionaryRepository {

    suspend fun getDictionary(word: String): ResultWrapper<Dictionary>
    suspend fun getSavedDictionary(word: String): ResultWrapper<Dictionary>
    suspend fun saveDictionary(dictionary: Dictionary)
}