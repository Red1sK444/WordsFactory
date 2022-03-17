package com.r3d1r4ph.wordsfactory.data.dictionary

import com.r3d1r4ph.wordsfactory.domain.Dictionary
import com.r3d1r4ph.wordsfactory.domain.Meaning
import kotlinx.serialization.Serializable

@Serializable
data class DictionaryDTO(
    val word: String,
    val phonetic: String? = null,
    val phonetics: List<PhoneticDTO>,
    val meanings: List<MeaningDTO>
) {
    fun toDomain(): Dictionary = Dictionary(
        word = word,
        phonetic = phonetic,
        audio = phonetics.find { it.text == phonetic }?.audio ?: "",
        partOfSpeech = meanings[0].partOfSpeech,
        meanings = meanings[0].definitions.map { it.toDomain() }
    )
}

@Serializable
data class PhoneticDTO(
    val text: String = "",
    val audio: String
)

@Serializable
data class MeaningDTO(
    val partOfSpeech: String,
    val definitions: List<DefinitionDTO>
)

@Serializable
data class DefinitionDTO(
    val definition: String,
    val example: String? = null
) {
    fun toDomain(): Meaning = Meaning(
        definition = definition,
        example = example
    )
}
