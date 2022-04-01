package com.r3d1r4ph.wordsfactory.data.auth

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.r3d1r4ph.wordsfactory.domain.Auth

@Entity(tableName = "auth")
data class AuthEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val email: String,
    val password: String
) {
    companion object {
        fun domainToEntity(domain: Auth) =
            AuthEntity(
                name = domain.name,
                email = domain.email,
                password = domain.password
            )
    }
}
