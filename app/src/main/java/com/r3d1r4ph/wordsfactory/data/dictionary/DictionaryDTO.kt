package com.r3d1r4ph.wordsfactory.data.dictionary

data class DictionaryDTO(
    val word: String,
    val phonetic: String,
    val phonetics: List<PhoneticDTO>,
    val meanings: List<MeaningDTO>
)

data class PhoneticDTO(
    val text: String,
    val audio: String
)

data class MeaningDTO(
    val partOfSpeech: String,
    val definitions: List<DefinitionDTO>
)

data class DefinitionDTO(
    val definition: String,
    val example: String?
)
