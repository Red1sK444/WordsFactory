package com.r3d1r4ph.wordsfactory.ui.onboarding

import androidx.annotation.StringRes
import com.r3d1r4ph.wordsfactory.R
import com.r3d1r4ph.wordsfactory.domain.IntroItem

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

fun IntroEnum.getIntroItem(): IntroItem = when (this) {
    IntroEnum.FIRST -> IntroItem(
        image = R.drawable.img_onboarding_1,
        title = R.string.onboarding_intro_1_title
    )
    IntroEnum.SECOND -> IntroItem(
        image = R.drawable.img_onboarding_2,
        title = R.string.onboarding_intro_2_title
    )
    IntroEnum.THIRD -> IntroItem(
        image = R.drawable.img_onboarding_3,
        title = R.string.onboarding_intro_3_title
    )
}