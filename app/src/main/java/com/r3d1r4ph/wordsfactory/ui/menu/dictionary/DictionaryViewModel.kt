package com.r3d1r4ph.wordsfactory.ui.menu.dictionary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.r3d1r4ph.wordsfactory.data.dictionary.DictionaryService
import kotlinx.coroutines.launch

class DictionaryViewModel : ViewModel() {

    private val dictionaryService = DictionaryService()

    private val _uiState = MutableLiveData(
        DictionaryUiState(
            dictionary = null,
            noWord = true
        )
    )
    val uiState: LiveData<DictionaryUiState>
        get() = _uiState

    fun search(word: String) {
        viewModelScope.launch {
            val response = dictionaryService.getDictionary(word)
            if (response.isSuccessful) {
                response.body()?.get(0)?.let {
                    _uiState.value = DictionaryUiState(
                        dictionary = it.toDomain(),
                        noWord = false
                    )
                }
            } else {
                _uiState.value = DictionaryUiState(
                    dictionary = null,
                    noWord = true
                )
            }
        }
    }
}