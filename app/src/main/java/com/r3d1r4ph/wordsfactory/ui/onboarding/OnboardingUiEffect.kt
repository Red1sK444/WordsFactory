package com.r3d1r4ph.wordsfactory.ui.onboarding

sealed class OnboardingUiEffect {
    object OpenSignUpScreen: OnboardingUiEffect()
    object OpenDictionaryScreen: OnboardingUiEffect()
}