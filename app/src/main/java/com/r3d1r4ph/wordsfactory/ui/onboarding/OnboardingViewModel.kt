package com.r3d1r4ph.wordsfactory.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OnboardingViewModel : ViewModel() {

    private val _uiState =
        MutableLiveData(
            OnboardingUiState(
                currentIntro = IntroEnum.FIRST
            )
        )
    val uiState: LiveData<OnboardingUiState>
        get() = _uiState

    val introList = IntroEnum.values().toMutableList()

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