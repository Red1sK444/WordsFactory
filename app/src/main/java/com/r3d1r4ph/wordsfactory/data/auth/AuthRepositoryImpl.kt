package com.r3d1r4ph.wordsfactory.data.auth

import com.r3d1r4ph.wordsfactory.domain.models.Auth
import com.r3d1r4ph.wordsfactory.domain.interfaces.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDao: AuthDao
) : AuthRepository {

    override suspend fun insertAuth(auth: Auth) {
        authDao.insert(AuthEntity.domainToEntity(auth))
    }

    override suspend fun checkAuth() =
        authDao.getAuth().isNotEmpty()
}