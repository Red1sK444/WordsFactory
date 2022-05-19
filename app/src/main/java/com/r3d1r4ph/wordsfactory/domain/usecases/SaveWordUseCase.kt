package com.r3d1r4ph.wordsfactory.domain.usecases

import com.r3d1r4ph.wordsfactory.domain.exceptions.WordNotSavedException
import com.r3d1r4ph.wordsfactory.domain.interfaces.DictionaryRepository
import com.r3d1r4ph.wordsfactory.domain.models.Dictionary
import com.r3d1r4ph.wordsfactory.domain.usecases.templates.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface SaveWordDictionaryUseCase : UseCase<Dictionary, Result<Unit>>

class SaveWordDictionaryUseCaseImpl @Inject constructor(
    private val dictionaryRepository: DictionaryRepository
) : SaveWordDictionaryUseCase {
    companion object {
        const val FAILED_INSERT = -1L
    }

    override suspend fun execute(input: Dictionary): Result<Unit> = withContext(Dispatchers.IO) {
        if (dictionaryRepository.saveDictionary(input) != FAILED_INSERT) {
            Result.success(Unit)
        } else {
            Result.failure(WordNotSavedException())
        }
    }

}