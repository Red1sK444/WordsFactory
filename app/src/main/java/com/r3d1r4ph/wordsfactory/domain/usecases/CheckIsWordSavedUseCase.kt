package com.r3d1r4ph.wordsfactory.domain.usecases

import com.r3d1r4ph.wordsfactory.domain.interfaces.DictionaryRepository
import com.r3d1r4ph.wordsfactory.domain.usecases.templates.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CheckIsWordSavedUseCase : UseCase<String, Boolean>

class CheckIsWordSavedUseCaseImpl @Inject constructor(
    private val dictionaryRepository: DictionaryRepository
) : CheckIsWordSavedUseCase {
    override suspend fun execute(input: String): Boolean = withContext(Dispatchers.IO) {
        dictionaryRepository.getSavedDictionary(input) != null
    }
}