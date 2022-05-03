package com.r3d1r4ph.wordsfactory.ui.onboarding

import androidx.lifecycle.*
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
        get() = _uiState.map { it }

    private val _uiAction = MutableLiveData<Event<OnboardingAction>>()
    val uiAction: LiveData<Event<OnboardingAction>>
        get() = _uiAction.map { it }

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
        _uiAction.value = Event(OnboardingAction.OpenDictionaryScreen)
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