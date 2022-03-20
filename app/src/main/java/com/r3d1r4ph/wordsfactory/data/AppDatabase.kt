package com.r3d1r4ph.wordsfactory.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.r3d1r4ph.wordsfactory.data.dictionary.DictionaryDao
import com.r3d1r4ph.wordsfactory.data.dictionary.DictionaryEntity
import com.r3d1r4ph.wordsfactory.data.dictionary.MeaningDao
import com.r3d1r4ph.wordsfactory.data.dictionary.MeaningEntity

@Database(
    entities = [DictionaryEntity::class, MeaningEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDictionaryDao(): DictionaryDao
    abstract fun getMeaningDao(): MeaningDao
}