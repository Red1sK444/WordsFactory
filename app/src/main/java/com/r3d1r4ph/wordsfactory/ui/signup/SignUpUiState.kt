package com.r3d1r4ph.wordsfactory.ui.signup

import androidx.annotation.StringRes
import com.r3d1r4ph.wordsfactory.R

data class SignUpUiState(
    @StringRes val nameError: Int = R.string.empty,
    @StringRes val emailError: Int = R.string.empty,
    @StringRes val passwordError: Int = R.string.empty,
    val openDictionaryScreen: Boolean = false
)
