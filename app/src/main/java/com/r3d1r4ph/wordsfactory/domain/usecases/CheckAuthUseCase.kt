package com.r3d1r4ph.wordsfactory.domain.usecases

import com.r3d1r4ph.wordsfactory.domain.interfaces.AuthRepository
import com.r3d1r4ph.wordsfactory.domain.usecases.templates.UseCaseWithoutParam
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CheckAuthUseCase : UseCaseWithoutParam<Boolean>

class CheckAuthUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : CheckAuthUseCase {
    override suspend fun execute(): Boolean = withContext(Dispatchers.IO) {
        authRepository.getAuth().isNotEmpty()
    }
}