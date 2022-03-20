package com.r3d1r4ph.wordsfactory.data.dictionary

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.r3d1r4ph.wordsfactory.domain.Meaning

@Entity(tableName = "meaning")
data class MeaningEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val dictionaryWord: String,
    val definition: String,
    val example: String?
) {
    fun toDomain(): Meaning =
        Meaning(
            definition = definition,
            example = example
        )
}