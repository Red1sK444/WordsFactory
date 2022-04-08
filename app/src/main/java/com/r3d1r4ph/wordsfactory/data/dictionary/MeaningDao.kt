package com.r3d1r4ph.wordsfactory.data.dictionary

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface MeaningDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeanings(meanings: List<MeaningEntity>)
}