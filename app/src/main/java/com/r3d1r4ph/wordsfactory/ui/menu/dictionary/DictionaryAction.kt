package com.r3d1r4ph.wordsfactory.ui.menu.dictionary

import com.r3d1r4ph.wordsfactory.ui.utils.ExceptionHolder

sealed class DictionaryAction {
    data class Error(val exceptionHolder: ExceptionHolder) : DictionaryAction()
}