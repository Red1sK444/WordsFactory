package com.r3d1r4ph.wordsfactory.domain.usecases

import com.r3d1r4ph.wordsfactory.domain.interfaces.AuthRepository
import javax.inject.Inject

class CheckAuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : UseCase<Unit, Boolean> {
    override suspend fun invoke(input: Unit): Boolean =
        authRepository.getAuth().isNotEmpty()
}