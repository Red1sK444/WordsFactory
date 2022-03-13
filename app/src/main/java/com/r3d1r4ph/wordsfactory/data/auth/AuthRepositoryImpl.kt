package com.r3d1r4ph.wordsfactory.data.auth

import com.r3d1r4ph.wordsfactory.domain.Auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val authDao: AuthDao
) : AuthRepository {

    override suspend fun insertAuth(auth: Auth) {
        authDao.insert(auth.toEntity())
    }

    override suspend fun checkAuth() =
        authDao.getAuth().isNotEmpty()
}