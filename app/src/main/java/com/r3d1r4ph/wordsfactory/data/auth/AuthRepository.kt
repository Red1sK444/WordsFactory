package com.r3d1r4ph.wordsfactory.data.auth

import com.r3d1r4ph.wordsfactory.domain.Auth

interface AuthRepository {
    suspend fun insertAuth(auth: Auth)

    suspend fun checkAuth(): Boolean
}