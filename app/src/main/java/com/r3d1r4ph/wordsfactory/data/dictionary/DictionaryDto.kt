package com.r3d1r4ph.wordsfactory.data.dictionary

import com.r3d1r4ph.wordsfactory.domain.Dictionary
import com.r3d1r4ph.wordsfactory.domain.Meaning
import kotlinx.serialization.Serializable

@Serializable
data class DictionaryDto(
    val word: String,
    val phonetic: String? = null,
    val phonetics: List<PhoneticDto>,
    val meanings: List<MeaningDto>
) {
    fun toDomain(): Dictionary = Dictionary(
        word = word,
        phonetic = phonetic,
        audio = phonetics.firstOrNull()?.audio.orEmpty(),
        partOfSpeech = meanings[0].partOfSpeech,
        meanings = meanings[0].definitions.map { it.toDomain() }
    )
}

@Serializable
data class PhoneticDto(
    val text: String = "",
    val audio: String
)

@Serializable
data class MeaningDto(
    val partOfSpeech: String,
    val definitions: List<DefinitionDto>
)

@Serializable
data class DefinitionDto(
    val definition: String,
    val example: String? = null
) {
    fun toDomain(): Meaning = Meaning(
        definition = definition,
        example = example
    )
}
