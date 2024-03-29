package com.r3d1r4ph.wordsfactory.ui.menu.dictionary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.r3d1r4ph.wordsfactory.R
import com.r3d1r4ph.wordsfactory.data.exceptions.NoConnectivityException
import com.r3d1r4ph.wordsfactory.data.exceptions.StatusCodeException
import com.r3d1r4ph.wordsfactory.domain.exceptions.NoWordException
import com.r3d1r4ph.wordsfactory.domain.exceptions.NotFoundException
import com.r3d1r4ph.wordsfactory.domain.exceptions.WordNotSavedException
import com.r3d1r4ph.wordsfactory.domain.usecases.CheckIsWordSavedUseCase
import com.r3d1r4ph.wordsfactory.domain.usecases.FindWordInDictionaryUseCase
import com.r3d1r4ph.wordsfactory.domain.usecases.SaveWordDictionaryUseCase
import com.r3d1r4ph.wordsfactory.domain.usecases.ValidateInputFieldUseCase
import com.r3d1r4ph.wordsfactory.domain.validation.ValidationRule
import com.r3d1r4ph.wordsfactory.ui.utils.Event
import com.r3d1r4ph.wordsfactory.ui.utils.ExceptionHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val validateInputFieldUseCase: ValidateInputFieldUseCase,
    private val findWordInDictionaryUseCase: FindWordInDictionaryUseCase,
    private val checkIsWordSavedUseCase: CheckIsWordSavedUseCase,
    private val saveWordDictionaryUseCase: SaveWordDictionaryUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData(DictionaryUiState())
    val uiState: LiveData<DictionaryUiState>
        get() = _uiState

    private val _uiAction = MutableLiveData<Event<DictionaryAction>>()
    val uiAction: LiveData<Event<DictionaryAction>>
        get() = _uiAction

    fun search(word: String) {
        viewModelScope.launch {
            if (!isWordCorrect(word)) {
                return@launch
            }

            uiState.value?.let {
                _uiState.value = it.copy(isLoading = true)
            }

            findWord(word)

            _uiState.value?.let {
                _uiState.postValue(
                    it.copy(
                        isLoading = false
                    )
                )
            }
        }
    }

    private suspend fun isWordCorrect(word: String): Boolean {
        validateInputFieldUseCase.execute(ValidationRule.NotBlank(word))
            .onSuccess {
                _uiState.value?.let {
                    _uiState.value =
                        it.copy(
                            searchError = null
                        )
                }
                return true
            }
            .onFailure {
                _uiState.value?.let {
                    _uiState.value =
                        it.copy(
                            searchError = R.string.empty_field
                        )
                }
            }
        return false
    }

    private suspend fun findWord(word: String) {
        findWordInDictionaryUseCase.execute(word)
            .onSuccess { dictionary ->
                uiState.value?.let {
                    _uiState.value = it.copy(
                        wordUiState = WordUiState.Success(
                            dictionary = dictionary,
                            isWordSaved = checkIsWordSavedUseCase.execute(word)
                        )
                    )
                }
            }
            .onFailure { throwable ->
                val exceptionHolder: ExceptionHolder
                when (throwable) {
                    is NoWordException, is NotFoundException -> {
                        uiState.value?.let {
                            _uiState.value = it.copy(
                                wordUiState = WordUiState.NoWord
                            )
                        }
                        return@onFailure
                    }
                    is NoConnectivityException -> {
                        exceptionHolder =
                            ExceptionHolder.Resource(
                                R.string.exception_no_internet_connection
                            )
                    }
                    is StatusCodeException -> {
                        exceptionHolder =
                            when (val message = throwable.message) {
                                null -> ExceptionHolder.Resource(R.string.unknown_exception)
                                else -> ExceptionHolder.Server(message)
                            }
                    }
                    else -> {
                        exceptionHolder =
                            ExceptionHolder.Resource(
                                R.string.unknown_exception
                            )
                    }
                }
                _uiAction.value = Event(DictionaryAction.Error(exceptionHolder))
            }
    }

    fun dismissValidationError() {
        viewModelScope.launch {
            _uiState.value?.let {
                _uiState.postValue(
                    it.copy(
                        searchError = null
                    )
                )
            }
        }
    }

    fun addToSaved() {
        viewModelScope.launch {
            _uiState.value?.let { state ->
                if (state.wordUiState is WordUiState.Success) {
                    saveWordDictionaryUseCase.execute(state.wordUiState.dictionary)
                        .onSuccess {
                            _uiState.value = state.copy(
                                wordUiState = state.wordUiState.copy(
                                    isWordSaved = true
                                )
                            )
                        }
                        .onFailure { throwable ->
                            _uiAction.value = Event(
                                DictionaryAction.Error(
                                    if (throwable is WordNotSavedException) {
                                        ExceptionHolder.Resource(
                                            R.string.word_not_saved
                                        )
                                    } else {
                                        ExceptionHolder.Resource(
                                            R.string.unknown_exception
                                        )
                                    }
                                )
                            )
                        }
                }
            }
        }
    }
}