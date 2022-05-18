package com.r3d1r4ph.wordsfactory.ui.signup

import androidx.annotation.StringRes
import com.r3d1r4ph.wordsfactory.R

data class SignUpUiState(
    @StringRes val nameError: Int? = null,
    @StringRes val emailError: Int? = null,
    @StringRes val passwordError: Int? = null,
    val authorizing: Boolean = false
)