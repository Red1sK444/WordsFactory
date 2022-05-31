package com.r3d1r4ph.wordsfactory.data.dictionary

import androidx.room.*

@Dao
interface DictionaryDao {

    @Query("SELECT * FROM dictionary WHERE word=:word")
    suspend fun getWordDictionary(word: String): DictionaryWithMeanings?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDictionary(dictionaryEntity: DictionaryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeanings(meanings: List<MeaningEntity>): List<Long>

    @Transaction
    suspend fun insertWordDictionary(
        dictionaryEntity: DictionaryEntity,
        meanings: List<MeaningEntity>
    ): Long {
        val dictionaryResult = insertDictionary(dictionaryEntity)
        val meaningsResult = insertMeanings(meanings)

        return if (dictionaryResult > 0 && meaningsResult.all { it > 0 }) {
            dictionaryResult
        } else {
            0
        }
    }
}