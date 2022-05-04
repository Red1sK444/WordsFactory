package com.r3d1r4ph.wordsfactory.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface IsFieldEmptyUseCase : UseCase<String, Boolean>

class IsFieldEmptyUseCaseImpl : IsFieldEmptyUseCase {
    override suspend fun execute(input: String): Boolean = withContext(Dispatchers.Default) {
        input.isBlank()
    }
}