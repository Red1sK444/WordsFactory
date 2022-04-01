package com.r3d1r4ph.wordsfactory.ui.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.r3d1r4ph.wordsfactory.R

enum class IntroEnum {
    FIRST,
    SECOND,
    THIRD
}

@StringRes
fun IntroEnum.getButtonTextRes(): Int = when (this) {
    IntroEnum.FIRST -> R.string.onboarding_button_next
    IntroEnum.SECOND -> R.string.onboarding_button_next
    IntroEnum.THIRD -> R.string.onboarding_button_lets_start
}

@DrawableRes
fun IntroEnum.getIntroImage(): Int = when (this) {
    IntroEnum.FIRST -> R.drawable.img_onboarding_1
    IntroEnum.SECOND -> R.drawable.img_onboarding_2
    IntroEnum.THIRD -> R.drawable.img_onboarding_3
}

@StringRes
fun IntroEnum.getIntroTitle(): Int = when (this) {
    IntroEnum.FIRST -> R.string.onboarding_intro_1_title
    IntroEnum.SECOND -> R.string.onboarding_intro_2_title
    IntroEnum.THIRD -> R.string.onboarding_intro_3_title
}