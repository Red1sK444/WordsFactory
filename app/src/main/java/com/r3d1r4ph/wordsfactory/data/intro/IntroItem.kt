package com.r3d1r4ph.wordsfactory.data.intro

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class IntroItem(
    @DrawableRes val image: Int,
    @StringRes val title: Int
)