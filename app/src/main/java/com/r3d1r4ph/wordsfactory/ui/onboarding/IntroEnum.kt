package com.r3d1r4ph.wordsfactory.ui.onboarding

import androidx.annotation.StringRes
import com.r3d1r4ph.wordsfactory.R

enum class IntroEnum(
    @StringRes val buttonTextId: Int,
    val openSignUpScreen: Boolean
) {
    FIRST(
        buttonTextId = R.string.onboarding_button_next,
        openSignUpScreen = false
    ),
    SECOND(
        buttonTextId = R.string.onboarding_button_next,
        openSignUpScreen = false
    ),
    THIRD(
        buttonTextId = R.string.onboarding_button_lets_start,
        openSignUpScreen = true
    )
}