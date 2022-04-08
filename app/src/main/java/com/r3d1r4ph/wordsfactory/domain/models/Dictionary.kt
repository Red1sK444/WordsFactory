package com.r3d1r4ph.wordsfactory.domain.models

data class Dictionary(
    val word: String,
    val phonetic: String?,
    val audio: String,
    val partOfSpeech: String,
    val meanings: List<Meaning>
)
