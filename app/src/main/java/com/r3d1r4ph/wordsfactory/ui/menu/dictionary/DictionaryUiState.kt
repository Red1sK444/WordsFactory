package com.r3d1r4ph.wordsfactory.ui.menu.dictionary

import com.r3d1r4ph.wordsfactory.domain.Dictionary

data class DictionaryUiState(
    val dictionary: Dictionary?,
    val noWord: Boolean
)
