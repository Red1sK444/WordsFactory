package com.r3d1r4ph.wordsfactory.domain.usecases

import com.r3d1r4ph.wordsfactory.domain.interfaces.AuthRepository
import javax.inject.Inject

interface CheckAuthUseCase : UseCase<Unit, Boolean>

class CheckAuthUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : CheckAuthUseCase {
    override suspend fun invoke(input: Unit): Boolean =
        authRepository.getAuth().isNotEmpty()
}