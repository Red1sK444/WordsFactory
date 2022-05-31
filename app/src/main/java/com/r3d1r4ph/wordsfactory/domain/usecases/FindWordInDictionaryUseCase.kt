package com.r3d1r4ph.wordsfactory.domain.usecases

import com.r3d1r4ph.wordsfactory.data.exceptions.NoConnectivityException
import com.r3d1r4ph.wordsfactory.domain.interfaces.DictionaryRepository
import com.r3d1r4ph.wordsfactory.domain.models.Dictionary
import com.r3d1r4ph.wordsfactory.domain.usecases.templates.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface FindWordInDictionaryUseCase : UseCase<String, Result<Dictionary>>

class FindWordInDictionaryUseCaseImpl @Inject constructor(
    private val dictionaryRepository: DictionaryRepository
) : FindWordInDictionaryUseCase {
    override suspend fun execute(input: String): Result<Dictionary> = withContext(Dispatchers.IO) {
        try {
            Result.success(dictionaryRepository.getDictionary(input))
        } catch (throwable: Throwable) {
            if (throwable is NoConnectivityException) {
                val wordDictionary = dictionaryRepository.getSavedDictionary(input)

                if (wordDictionary != null) {
                    return@withContext Result.success(wordDictionary)
                }
            }
            Result.failure(throwable)
        }
    }
}