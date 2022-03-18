package com.r3d1r4ph.wordsfactory.data.dictionary

import com.r3d1r4ph.wordsfactory.domain.Dictionary

interface DictionaryRepository {

    suspend fun getDictionary(word: String): Dictionary?
}