package com.r3d1r4ph.wordsfactory.domain.interfaces

import com.r3d1r4ph.wordsfactory.domain.models.Auth

interface AuthRepository {
    suspend fun insertAuth(auth: Auth): Long

    suspend fun getAuth(): List<Auth>
}