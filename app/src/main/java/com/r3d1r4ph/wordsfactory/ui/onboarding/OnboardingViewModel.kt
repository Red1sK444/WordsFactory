package com.r3d1r4ph.wordsfactory.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.r3d1r4ph.wordsfactory.domain.usecases.CheckAuthUseCase
import com.r3d1r4ph.wordsfactory.ui.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val checkAuthUseCase: CheckAuthUseCase
) : ViewModel() {
    private companion object {
        const val COUNT_TO_NEXT = 1
    }

    private val _uiState =
        MutableLiveData(
            OnboardingUiState(
                currentIntro = IntroEnum.FIRST
            )
        )
    val uiState: LiveData<OnboardingUiState>
        get() = _uiState

    private val _openDictionaryScreenUiEffect = MutableLiveData<Event<Boolean>>()
    val openDictionaryScreenUiEffect: LiveData<Event<Boolean>>
        get() = _openDictionaryScreenUiEffect

    val introList = IntroEnum.values().toMutableList()

    init {
        checkAuth()
    }

    private fun checkAuth() {
        viewModelScope.launch {
            if (checkAuthUseCase.execute()) {
                openDictionaryScreen()
            }
        }
    }

    private fun openDictionaryScreen() {
        _openDictionaryScreenUiEffect.value = Event(true)
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
                currentIntro = IntroEnum.values()[it.currentIntro.ordinal.plus(COUNT_TO_NEXT)]
            )
        }
    }
}