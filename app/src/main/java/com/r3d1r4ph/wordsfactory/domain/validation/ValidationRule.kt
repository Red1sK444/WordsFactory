package com.r3d1r4ph.wordsfactory.domain.validation

import com.r3d1r4ph.wordsfactory.domain.exceptions.EmptyFieldException
import com.r3d1r4ph.wordsfactory.domain.exceptions.NoAtSignException

sealed class ValidationRule(protected val input: String) {
    class NotEmpty(input: String) : ValidationRule(input) {
        override fun apply(): Result<Unit> =
            when {
                input.isBlank() -> Result.failure(EmptyFieldException())
                else -> Result.success(Unit)
            }
    }

    class IsEmail(input: String) : ValidationRule(input) {
        private companion object {
            const val AT_SIGN = '@'
        }

        override fun apply(): Result<Unit> =
            when {
                input.isBlank() -> Result.failure(EmptyFieldException())
                !input.contains(AT_SIGN) -> Result.failure(NoAtSignException())
                else -> Result.success(Unit)
            }
    }

    abstract fun apply(): Result<Unit>
}