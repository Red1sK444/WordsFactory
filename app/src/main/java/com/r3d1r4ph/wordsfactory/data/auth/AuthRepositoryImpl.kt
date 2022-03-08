package com.r3d1r4ph.wordsfactory.data.auth

import com.r3d1r4ph.wordsfactory.domain.Auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val authDao: AuthDao,
    private val externalScope: CoroutineScope
) : AuthRepository {

    override suspend fun insertAuth(auth: Auth) {
        withContext(externalScope.coroutineContext) {
            authDao.insert(auth.toEntity())
        }
    }

    override suspend fun checkAuth() =
        authDao.getAuth().isNotEmpty()
}