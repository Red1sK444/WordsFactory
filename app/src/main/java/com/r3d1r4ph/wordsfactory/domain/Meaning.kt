package com.r3d1r4ph.wordsfactory.domain

import com.r3d1r4ph.wordsfactory.data.dictionary.MeaningEntity

data class Meaning(
    val definition: String,
    val example: String?
) {
    fun toEntity(word: String): MeaningEntity =
        MeaningEntity(
            dictionaryWord = word,
            definition = definition,
            example = example
        )
}
