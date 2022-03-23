package com.r3d1r4ph.wordsfactory.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.r3d1r4ph.wordsfactory.R
import com.r3d1r4ph.wordsfactory.domain.IntroItem

class OnboardingViewModel : ViewModel() {

    private val _uiState =
        MutableLiveData(
            OnboardingUiState(
                currentIntro = IntroEnum.FIRST
            )
        )
    val uiState: LiveData<OnboardingUiState>
        get() = _uiState

    val introList = listOf(
        IntroItem(
            image = R.drawable.img_onboarding_1,
            title = R.string.onboarding_intro_1_title
        ),
        IntroItem(
            image = R.drawable.img_onboarding_2,
            title = R.string.onboarding_intro_2_title
        ),
        IntroItem(
            image = R.drawable.img_onboarding_3,
            title = R.string.onboarding_intro_3_title
        )
    )

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