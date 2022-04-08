package com.r3d1r4ph.wordsfactory.domain

interface AuthRepository {
    suspend fun insertAuth(auth: Auth)

    suspend fun checkAuth(): Boolean
}