package com.r3d1r4ph.wordsfactory.data.dictionary

import com.r3d1r4ph.wordsfactory.data.utils.safeApiCall
import com.r3d1r4ph.wordsfactory.domain.exceptions.UnknownException
import com.r3d1r4ph.wordsfactory.domain.interfaces.DictionaryRepository
import com.r3d1r4ph.wordsfactory.domain.models.Dictionary
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(
    private val dictionaryService: DictionaryService,
    private val dictionaryDao: DictionaryDao
) : DictionaryRepository {

    override suspend fun getDictionary(word: String): Dictionary =
        safeApiCall(
            apiCall = {
                dictionaryService.getDictionary(word)
            },
            onSuccess = { response ->
                response.body()?.let { list ->
                    if (list.isNotEmpty()) {
                        return list[0].toDomain()
                    }
                    throw UnknownException()
                }
                throw UnknownException()
            }
        )


    override suspend fun getSavedDictionary(word: String): Dictionary? =
        dictionaryDao.getWordDictionary(word)?.toDomain()


    override suspend fun saveDictionary(dictionary: Dictionary): Long =
        dictionaryDao.insertWordDictionary(
            dictionaryEntity = DictionaryEntity.domainToEntity(dictionary),
            meanings = dictionary.meanings.map {
                MeaningEntity.domainToEntity(
                    domain = it,
                    word = dictionary.word
                )
            }
        )
}