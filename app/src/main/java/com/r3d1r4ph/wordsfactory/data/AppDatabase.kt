package com.r3d1r4ph.wordsfactory.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.r3d1r4ph.wordsfactory.data.auth.AuthDao
import com.r3d1r4ph.wordsfactory.data.auth.AuthEntity

@Database(
    entities = [AuthEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getAuthDao(): AuthDao
}