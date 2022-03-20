package com.r3d1r4ph.wordsfactory.domain

import com.r3d1r4ph.wordsfactory.data.dictionary.DictionaryEntity

data class Dictionary(
    val word: String,
    val phonetic: String?,
    val audio: String,
    val partOfSpeech: String,
    val meanings: List<Meaning>
) {
    fun toEntity(): DictionaryEntity =
        DictionaryEntity(
            word = word,
            phonetic = phonetic ?: "",
            audio = audio,
            partOfSpeech = partOfSpeech
        )
}
