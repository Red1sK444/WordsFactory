package com.r3d1r4ph.wordsfactory.ui.signup

import com.r3d1r4ph.wordsfactory.common.exceptions.ExceptionHolder

sealed class SignUpAction {
    object OpenDictionaryScreen : SignUpAction()
    data class Error(val exceptionHolder: ExceptionHolder) : SignUpAction()
}