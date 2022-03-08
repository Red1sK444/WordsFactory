package com.r3d1r4ph.wordsfactory.ui.signup

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.r3d1r4ph.wordsfactory.R
import com.r3d1r4ph.wordsfactory.data.Database
import com.r3d1r4ph.wordsfactory.data.auth.AuthRepository
import com.r3d1r4ph.wordsfactory.data.auth.AuthRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SignUpUiState(
    @StringRes val nameError: Int = R.string.empty,
    @StringRes val emailError: Int = R.string.empty,
    @StringRes val passwordError: Int = R.string.empty,
    val openDictionaryScreen: Boolean = false
)

class SignUpViewModel : ViewModel() {

//    private val repository = AuthRepositoryImpl(
//        authDao = Database.getInstance(),
//        externalScope =
//    )

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState>
        get() = _uiState.asStateFlow()

    fun signUp(
        name: String,
        email: String,
        password: String
    ) {
        val nameError = emptyFieldCheck(name)
        val emailError = emailFieldCheck(email)
        val passwordError = emptyFieldCheck(password)

        viewModelScope.launch {
            _uiState.update {
                SignUpUiState(
                    nameError = nameError,
                    emailError = emailError,
                    passwordError = passwordError,
                    openDictionaryScreen = nameError == R.string.empty
                            && emailError == R.string.empty
                            && passwordError == R.string.empty
                )
            }
        }
    }

    private fun emptyFieldCheck(
        text: String
    ): Int = if (text.isEmpty()) {
        R.string.signup_empty_field
    } else {
        R.string.empty
    }

    private fun emailFieldCheck(
        text: String
    ): Int {
        val empty = emptyFieldCheck(text)
        return if (empty != R.string.empty) {
            empty
        } else if (!text.contains('@')) {
            R.string.signup_not_e_mail
        } else {
            R.string.empty
        }
    }
}