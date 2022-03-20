package com.r3d1r4ph.wordsfactory.data.dictionary

import com.r3d1r4ph.wordsfactory.domain.Dictionary
import com.r3d1r4ph.wordsfactory.utils.ResultWrapper
import com.r3d1r4ph.wordsfactory.utils.exceptions.NoWordException
import com.r3d1r4ph.wordsfactory.utils.safeApiCall
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(
    private val dictionaryService: DictionaryService,
    private val dictionaryDao: DictionaryDao,
    private val meaningDao: MeaningDao
) : DictionaryRepository {

    override suspend fun getDictionary(word: String): ResultWrapper<Dictionary> {
        return safeApiCall(
            apiCall = {
                dictionaryService.getDictionary(word)
            },
            onSuccess = { response ->
                var result: ResultWrapper<Dictionary> = ResultWrapper.Failure(NoWordException())

                response.body()?.let { list ->
                    if (list.isNotEmpty()) {
                        result = ResultWrapper.Success(value = list[0].toDomain())
                    }
                }
                result
            })
    }


    override suspend fun getSavedDictionary(word: String): ResultWrapper<Dictionary> =
        when (val dictionary = dictionaryDao.getWordDictionary(word)?.toDomain()) {
            is Dictionary -> ResultWrapper.Success(dictionary)
            else -> ResultWrapper.Failure(NoWordException())
        }


    override suspend fun saveDictionary(dictionary: Dictionary) {
        dictionaryDao.insertDictionary(dictionaryEntity = dictionary.toEntity())
        meaningDao.insertMeanings(dictionary.meanings.map { it.toEntity(dictionary.word) })
    }
}