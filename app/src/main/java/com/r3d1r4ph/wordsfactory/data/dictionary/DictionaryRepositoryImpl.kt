package com.r3d1r4ph.wordsfactory.data.dictionary

import com.r3d1r4ph.wordsfactory.common.safeApiCall
import com.r3d1r4ph.wordsfactory.domain.exceptions.NoWordException
import com.r3d1r4ph.wordsfactory.domain.interfaces.DictionaryRepository
import com.r3d1r4ph.wordsfactory.domain.models.Dictionary
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(
    private val dictionaryService: DictionaryService,
    private val dictionaryDao: DictionaryDao
) : DictionaryRepository {

    override suspend fun getDictionary(word: String): Result<Dictionary> {
        return safeApiCall(
            apiCall = {
                dictionaryService.getDictionary(word)
            },
            onSuccess = { response ->
                var result: Result<Dictionary> = Result.failure(NoWordException())

                response.body()?.let { list ->
                    if (list.isNotEmpty()) {
                        result = Result.success(value = list[0].toDomain())
                    }
                }
                result
            }
        )
    }


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