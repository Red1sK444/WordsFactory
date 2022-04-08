package com.r3d1r4ph.wordsfactory.data.dictionary

import com.r3d1r4ph.wordsfactory.domain.models.Dictionary
import com.r3d1r4ph.wordsfactory.domain.interfaces.DictionaryRepository
import com.r3d1r4ph.wordsfactory.utils.exceptions.NoWordException
import com.r3d1r4ph.wordsfactory.utils.safeApiCall
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(
    private val dictionaryService: DictionaryService,
    private val dictionaryDao: DictionaryDao,
    private val meaningDao: MeaningDao
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
            })
    }


    override suspend fun getSavedDictionary(word: String): Result<Dictionary> =
        when (val dictionary = dictionaryDao.getWordDictionary(word)?.toDomain()) {
            is Dictionary -> Result.success(dictionary)
            else -> Result.failure(NoWordException())
        }


    override suspend fun saveDictionary(dictionary: Dictionary) {
        dictionaryDao.insertDictionary(dictionaryEntity = DictionaryEntity.domainToEntity(dictionary))
        meaningDao.insertMeanings(dictionary.meanings.map {
            MeaningEntity.domainToEntity(
                domain = it,
                word = dictionary.word
            )
        })
    }
}