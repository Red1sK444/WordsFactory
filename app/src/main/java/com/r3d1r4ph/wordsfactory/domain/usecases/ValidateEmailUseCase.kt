package com.r3d1r4ph.wordsfactory.domain.usecases

import com.r3d1r4ph.wordsfactory.common.exceptions.EmptyFieldException
import com.r3d1r4ph.wordsfactory.common.exceptions.NoAtSignException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface ValidateEmailUseCase : UseCase<String, Result<Unit>>

class ValidateEmailUseCaseImpl @Inject constructor() : ValidateEmailUseCase {
    companion object {
        const val AT_SIGN = '@'
    }

    override suspend fun execute(input: String): Result<Unit> = withContext(Dispatchers.Default) {
        when {
            input.isBlank() -> Result.failure(EmptyFieldException())
            !input.contains(AT_SIGN) -> Result.failure(NoAtSignException())
            else -> Result.success(Unit)
        }
    }
}