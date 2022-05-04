package com.r3d1r4ph.wordsfactory.domain.usecases

import com.r3d1r4ph.wordsfactory.ui.signup.SignUpViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface IsEmailFieldUseCase : UseCase<String, Boolean>

class IsEmailFieldUseCaseImpl @Inject constructor(
    private val isFieldEmptyUseCase: IsFieldEmptyUseCase
): IsEmailFieldUseCase {
    companion object {
        const val AT_SIGN = '@'
    }

    override suspend fun execute(input: String): Boolean = withContext(Dispatchers.Default) {
        !isFieldEmptyUseCase.execute(input) && input.contains(AT_SIGN)
    }
}