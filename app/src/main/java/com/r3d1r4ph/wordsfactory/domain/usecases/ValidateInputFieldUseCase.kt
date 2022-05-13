package com.r3d1r4ph.wordsfactory.domain.usecases

import com.r3d1r4ph.wordsfactory.domain.validation.ValidationRuleEnum
import com.r3d1r4ph.wordsfactory.domain.validation.apply
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface ValidateInputFieldUseCase : UseCase<Pair<String, ValidationRuleEnum>, Result<Unit>>

class ValidateInputFieldUseCaseImpl @Inject constructor() : ValidateInputFieldUseCase {
    override suspend fun execute(input: Pair<String, ValidationRuleEnum>): Result<Unit> =
        withContext(Dispatchers.Default) {
            val (inputField, validationRule) = input
            validationRule.apply(inputField)
        }
}

