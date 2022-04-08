package com.r3d1r4ph.wordsfactory.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.r3d1r4ph.wordsfactory.domain.interfaces.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState =
        MutableLiveData(
            OnboardingUiState(
                currentIntro = IntroEnum.FIRST
            )
        )
    val uiState: LiveData<OnboardingUiState>
        get() = _uiState

    val introList = IntroEnum.values().toMutableList()

    init {
        checkAuth()
    }

    private fun checkAuth() {
        viewModelScope.launch {
            _uiState.postValue(
                _uiState.value?.copy(openDictionaryScreen = authRepository.checkAuth())
            )
        }
    }

    fun getCurrentIntro(): IntroEnum? =
        uiState.value?.currentIntro

    fun updateCurrentIntroByGesture(intro: Int) {
        _uiState.value = OnboardingUiState(
            currentIntro = IntroEnum.values()[intro]
        )
    }

    fun toNextIntro() {
        _uiState.value = uiState.value?.let {
            OnboardingUiState(
                currentIntro = IntroEnum.values()[it.currentIntro.ordinal + 1]
            )
        }
    }
}