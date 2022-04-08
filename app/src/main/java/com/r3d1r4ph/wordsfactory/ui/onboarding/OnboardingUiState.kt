package com.r3d1r4ph.wordsfactory.ui.onboarding

data class OnboardingUiState(
    val currentIntro: IntroEnum,
    val openDictionaryScreen: Boolean = false
)