package com.r3d1r4ph.wordsfactory.domain.usecases

import com.r3d1r4ph.wordsfactory.common.exceptions.EmptyFieldException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ValidateNameUseCase: UseCase<String, Result<Unit>>

class ValidateNameUseCaseImpl: ValidateNameUseCase {
    override suspend fun execute(input: String): Result<Unit> = withContext(Dispatchers.Default) {
        if (input.isBlank()) {
            Result.failure(EmptyFieldException())
        } else {
            Result.success(Unit)
        }
    }
}