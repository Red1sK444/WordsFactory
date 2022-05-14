package com.r3d1r4ph.wordsfactory.ui.utils

import androidx.annotation.StringRes

sealed class ExceptionHolder {
    data class Server(val message: String): ExceptionHolder()
    data class Resource(@StringRes val messageId: Int): ExceptionHolder()
}
