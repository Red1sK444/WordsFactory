package com.r3d1r4ph.wordsfactory.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.r3d1r4ph.wordsfactory.R
import com.r3d1r4ph.wordsfactory.data.auth.AuthRepository
import com.r3d1r4ph.wordsfactory.domain.Auth
import kotlinx.coroutines.launch

class SignUpViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _uiState = MutableLiveData(SignUpUiState())
    val uiState: LiveData<SignUpUiState>
        get() = _uiState

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

            _uiState.value =
                SignUpUiState(
                    nameError = nameError,
                    emailError = emailError,
                    passwordError = passwordError,
                    openDictionaryScreen = authRepository.checkAuth()
                )
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
            _uiState.value = _uiState.value?.copy(openDictionaryScreen = authRepository.checkAuth())
        }
    }

}