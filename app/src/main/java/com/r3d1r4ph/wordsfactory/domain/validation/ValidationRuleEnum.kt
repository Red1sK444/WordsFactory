package com.r3d1r4ph.wordsfactory.domain.validation

import com.r3d1r4ph.wordsfactory.common.exceptions.EmptyFieldException
import com.r3d1r4ph.wordsfactory.common.exceptions.NoAtSignException
import com.r3d1r4ph.wordsfactory.domain.validation.ValidationRuleEnum.Companion.AT_SIGN

enum class ValidationRuleEnum {
    EMPTY,
    EMAIL;

    companion object {
        const val AT_SIGN = '@'
    }
}

fun ValidationRuleEnum.apply(input: String): Result<Unit> = when (this) {
    ValidationRuleEnum.EMPTY -> when {
        input.isBlank() -> Result.failure(EmptyFieldException())
        else -> Result.success(Unit)
    }
    ValidationRuleEnum.EMAIL -> when {
        input.isBlank() -> Result.failure(EmptyFieldException())
        !input.contains(AT_SIGN) -> Result.failure(NoAtSignException())
        else -> Result.success(Unit)
    }
}