package com.r3d1r4ph.wordsfactory.domain.usecases

import com.r3d1r4ph.wordsfactory.common.exceptions.NoAuthorizedException
import com.r3d1r4ph.wordsfactory.domain.interfaces.AuthRepository
import com.r3d1r4ph.wordsfactory.domain.models.Auth
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AuthUseCase : UseCase<Auth, Result<Unit>>

class AuthUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : AuthUseCase {
    companion object {
        const val FAILED_INSERT = -1L
    }

    override suspend fun execute(input: Auth): Result<Unit> = withContext(Dispatchers.IO) {
        if (authRepository.insertAuth(input) != FAILED_INSERT) {
            Result.success(Unit)
        } else {
            Result.failure(NoAuthorizedException())
        }
    }
}