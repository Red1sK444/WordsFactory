package com.r3d1r4ph.wordsfactory.data.dictionary

import com.r3d1r4ph.wordsfactory.domain.Dictionary
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(
    private val dictionaryService: DictionaryService
) : DictionaryRepository {

    override suspend fun getDictionary(word: String): Dictionary? =
        dictionaryService.getDictionary(word).body()?.get(0)?.toDomain()
}