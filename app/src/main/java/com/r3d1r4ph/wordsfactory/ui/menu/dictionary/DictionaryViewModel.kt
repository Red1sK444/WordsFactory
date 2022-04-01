package com.r3d1r4ph.wordsfactory.ui.menu.dictionary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.r3d1r4ph.wordsfactory.R
import com.r3d1r4ph.wordsfactory.data.dictionary.DictionaryRepository
import com.r3d1r4ph.wordsfactory.utils.exceptions.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val dictionaryRepository: DictionaryRepository
) : ViewModel() {

    private val _uiState = MutableLiveData(DictionaryUiState())
    val uiState: LiveData<DictionaryUiState>
        get() = _uiState

    private val _exception = MutableLiveData<ExceptionHolder>()
    val exception: LiveData<ExceptionHolder>
        get() = _exception

    fun search(word: String) {
        if (!isWordCorrect(word)) {
            return
        }

        _uiState.value?.let {
            _uiState.value =
                it.copy(
                    isLoading = true
                )
        }

        viewModelScope.launch {
            val response = dictionaryRepository.getDictionary(word)
            if (response.isSuccess) {
                val dictionary = response.getOrNull()
                _uiState.postValue(
                    DictionaryUiState(
                        dictionary = dictionary,
                        isWordSaved = isWordSaved(word),
                        noWord = false
                    )
                )
            } else {
                when (val exception = response.exceptionOrNull()) {
                    is NoConnectivityException -> {
                        savedSearch(word)
                    }
                    is NoWordException, is NotFoundException -> {
                        _uiState.postValue(
                            DictionaryUiState(
                                noWord = true
                            )
                        )
                    }
                    is StatusCodeException -> {
                        _exception.postValue(
                            when (val message = exception.message) {
                                null -> ExceptionHolder.Resource(R.string.unknown_exception)
                                else -> ExceptionHolder.Server(message)
                            }
                        )
                    }
                    else -> {
                        _exception.postValue(
                            ExceptionHolder.Resource(
                                R.string.unknown_exception
                            )
                        )
                    }
                }

            }
        }

        _uiState.value?.let {
            _uiState.postValue(
                it.copy(
                    isLoading = false
                )
            )
        }
    }

    private fun isWordCorrect(word: String): Boolean =
        if (word.isBlank()) {
            _uiState.value?.let {
                _uiState.value =
                    it.copy(
                        validation = R.string.empty_field
                    )
            }
            false
        } else {
            _uiState.value?.let {
                _uiState.value =
                    it.copy(
                        validation = R.string.empty
                    )
            }
            true
        }

    private suspend fun isWordSaved(word: String): Boolean {
        val response = dictionaryRepository.getSavedDictionary(word)
        return response.isSuccess
    }


    private suspend fun savedSearch(word: String) {
        val response = dictionaryRepository.getSavedDictionary(word)
        if (response.isSuccess) {
            val dictionary = response.getOrNull()
            _uiState.value = DictionaryUiState(
                dictionary = dictionary,
                isWordSaved = true,
                noWord = false
            )
        } else {
            when (response.exceptionOrNull()) {
                is NoWordException -> {
                    _exception.value = ExceptionHolder.Resource(
                        R.string.exception_no_internet_connection
                    )
                }
                else -> {
                    _exception.value = ExceptionHolder.Resource(
                        R.string.unknown_exception
                    )
                }
            }
        }
    }

    fun dismissValidationError() {
        viewModelScope.launch {
            _uiState.value?.let {
                _uiState.postValue(
                    it.copy(
                        validation = R.string.empty
                    )
                )
            }
        }
    }

    fun addToSaved() {
        viewModelScope.launch {
            _uiState.value?.let {
                it.dictionary?.let { dict ->
                    dictionaryRepository.saveDictionary(dict)

                    _uiState.value = it.copy(
                        isWordSaved = true
                    )
                }
            }
        }
    }
}