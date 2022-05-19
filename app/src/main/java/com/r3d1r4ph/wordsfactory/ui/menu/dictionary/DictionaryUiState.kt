package com.r3d1r4ph.wordsfactory.ui.menu.dictionary

import androidx.annotation.StringRes
import com.r3d1r4ph.wordsfactory.domain.models.Dictionary

data class DictionaryUiState(
    @StringRes val searchError: Int? = null,
    val wordUiState: WordUiState = WordUiState.NoWord,
    val isLoading: Boolean = false
)

sealed class WordUiState {
    data class Success(val dictionary: Dictionary, val isWordSaved: Boolean) : WordUiState()
    object NoWord : WordUiState()
}
