package com.r3d1r4ph.wordsfactory.ui.signup

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.r3d1r4ph.wordsfactory.R
import com.r3d1r4ph.wordsfactory.data.auth.AuthRepository
import com.r3d1r4ph.wordsfactory.domain.Auth
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

class SignUpViewModel(private val authRepository: AuthRepository) : ViewModel() {


    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState>
        get() = _uiState.asStateFlow()

    fun signUp(
        auth: Auth
    ) {
        val nameError = emptyFieldCheck(auth.name)
        val emailError = emailFieldCheck(auth.email)
        val passwordError = emptyFieldCheck(auth.password)

        viewModelScope.launch {
            if (nameError == R.string.empty
                && emailError == R.string.empty
                && passwordError == R.string.empty
            ) {
                authorize(auth)
            }

            _uiState.update {
                SignUpUiState(
                    nameError = nameError,
                    emailError = emailError,
                    passwordError = passwordError,
                    openDictionaryScreen = authRepository.checkAuth()
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

    private suspend fun authorize(auth: Auth) {
        authRepository.insertAuth(auth)
    }

    fun checkAuth() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(openDictionaryScreen = authRepository.checkAuth())
            }
        }
    }

}