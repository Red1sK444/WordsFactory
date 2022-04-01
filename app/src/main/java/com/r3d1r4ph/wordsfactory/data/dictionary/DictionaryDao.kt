package com.r3d1r4ph.wordsfactory.data.dictionary

import androidx.room.*

@Dao
interface DictionaryDao {

    @Query("SELECT * FROM dictionary WHERE word=:word")
    suspend fun getWordDictionary(word: String): DictionaryWithMeanings?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDictionary(dictionaryEntity: DictionaryEntity)

}