package com.r3d1r4ph.wordsfactory.domain

import com.r3d1r4ph.wordsfactory.data.auth.AuthEntity

data class Auth(
    val name: String,
    val email: String,
    val password: String
) {
    fun toEntity() =
        AuthEntity(
            name = name,
            email = email,
            password = password
        )
}
