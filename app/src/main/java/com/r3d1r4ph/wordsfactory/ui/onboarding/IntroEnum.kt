package com.r3d1r4ph.wordsfactory.ui.onboarding

import androidx.annotation.StringRes
import com.r3d1r4ph.wordsfactory.R

enum class IntroEnum {
    FIRST,
    SECOND,
    THIRD
}

@StringRes
fun IntroEnum.getButtonTextRes(): Int = when (this) {
    IntroEnum.THIRD -> R.string.onboarding_button_lets_start
    else -> R.string.onboarding_button_next
}