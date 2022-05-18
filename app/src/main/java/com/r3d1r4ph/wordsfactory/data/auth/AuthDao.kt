package com.r3d1r4ph.wordsfactory.data.auth

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AuthDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(authEntity: AuthEntity): Long

    @Query("SELECT * FROM auth")
    suspend fun getAuth(): List<AuthEntity>
}