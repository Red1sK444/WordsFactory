package com.r3d1r4ph.wordsfactory.domain.models

import androidx.annotation.StringRes

data class ValidationResult(
    val successful: Boolean,
    @StringRes val errorMessage: Int? = null
)