package com.r3d1r4ph.wordsfactory.data.dictionary

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.r3d1r4ph.wordsfactory.domain.models.Dictionary

@Entity(tableName = "dictionary")
data class DictionaryEntity(
    @PrimaryKey(autoGenerate = false)
    val word: String,
    val phonetic: String,
    val audio: String,
    val partOfSpeech: String
) {
    companion object {
        fun domainToEntity(domain: Dictionary): DictionaryEntity =
            DictionaryEntity(
                word = domain.word,
                phonetic = domain.phonetic.orEmpty(),
                audio = domain.audio,
                partOfSpeech = domain.partOfSpeech
            )
    }
}

data class DictionaryWithMeanings(
    @Embedded val dictionary: DictionaryEntity,
    @Relation(
        parentColumn = "word",
        entityColumn = "dictionaryWord"
    )
    val meanings: List<MeaningEntity>
) {
    fun toDomain(): Dictionary =
        Dictionary(
            word = dictionary.word,
            phonetic = dictionary.phonetic,
            audio = dictionary.audio,
            partOfSpeech = dictionary.partOfSpeech,
            meanings = meanings.map { it.toDomain() }
        )
}
