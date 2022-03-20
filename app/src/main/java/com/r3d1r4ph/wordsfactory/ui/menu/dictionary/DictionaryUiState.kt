package com.r3d1r4ph.wordsfactory.ui.menu.dictionary

import androidx.annotation.StringRes
import com.r3d1r4ph.wordsfactory.R
import com.r3d1r4ph.wordsfactory.domain.Dictionary

data class DictionaryUiState(
    val dictionary: Dictionary? = null,
    val isWordSaved: Boolean = false,
    val noWord: Boolean = true,
    @StringRes val validation: Int = R.string.empty
)
