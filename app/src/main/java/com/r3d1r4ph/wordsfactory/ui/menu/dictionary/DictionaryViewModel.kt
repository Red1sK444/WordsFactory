package com.r3d1r4ph.wordsfactory.ui.menu.dictionary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.r3d1r4ph.wordsfactory.data.dictionary.DictionaryRepository
import com.r3d1r4ph.wordsfactory.data.dictionary.DictionaryRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val dictionaryRepository: DictionaryRepository
) : ViewModel() {

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
            val response = dictionaryRepository.getDictionary(word)
            if (response != null) {
                _uiState.value = DictionaryUiState(
                    dictionary = response,
                    noWord = false
                )
            } else {
                _uiState.value = DictionaryUiState(
                    dictionary = null,
                    noWord = true
                )
            }
        }
    }
}