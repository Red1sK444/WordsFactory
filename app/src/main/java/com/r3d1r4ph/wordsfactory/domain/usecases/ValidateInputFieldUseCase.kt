package com.r3d1r4ph.wordsfactory.domain.usecases

import com.r3d1r4ph.wordsfactory.domain.usecases.templates.UseCase
import com.r3d1r4ph.wordsfactory.domain.validation.ValidationRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface ValidateInputFieldUseCase : UseCase<ValidationRule, Result<Unit>>

class ValidateInputFieldUseCaseImpl @Inject constructor() : ValidateInputFieldUseCase {
    override suspend fun execute(input: ValidationRule): Result<Unit> =
        withContext(Dispatchers.Default) {
            input.apply()
        }
}

