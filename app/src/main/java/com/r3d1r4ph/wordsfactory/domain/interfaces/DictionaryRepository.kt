package com.r3d1r4ph.wordsfactory.domain.interfaces

import com.r3d1r4ph.wordsfactory.domain.models.Dictionary

interface DictionaryRepository {

    suspend fun getDictionary(word: String): Dictionary
    suspend fun getSavedDictionary(word: String): Dictionary?
    suspend fun saveDictionary(dictionary: Dictionary): Long
}