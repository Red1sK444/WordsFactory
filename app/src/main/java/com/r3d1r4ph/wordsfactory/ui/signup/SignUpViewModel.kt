package com.r3d1r4ph.wordsfactory.ui.signup

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.r3d1r4ph.wordsfactory.R
import com.r3d1r4ph.wordsfactory.data.auth.AuthRepository
import com.r3d1r4ph.wordsfactory.domain.Auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private companion object {
        const val AT_SIGN = '@'
    }

    private val _uiState = MutableLiveData(SignUpUiState())
    val uiState: LiveData<SignUpUiState>
        get() = _uiState

    fun signUp(
        name: String,
        email: String,
        password: String
    ) {
        val nameError = emptyFieldCheck(name)
        val emailError = emailFieldCheck(email)
        val passwordError = emptyFieldCheck(password)

        viewModelScope.launch {
            if (nameError == null
                && emailError == null
                && passwordError == null
            ) {
                authorize(
                    Auth(
                        name = name,
                        email = email,
                        password = password
                    )
                )
            }

            _uiState.postValue(
                SignUpUiState(
                    nameError = nameError,
                    emailError = emailError,
                    passwordError = passwordError,
                    openDictionaryScreen = authRepository.checkAuth()
                )
            )

        }
    }

    fun dismissError(inputFieldEnum: InputFieldEnum) {
        viewModelScope.launch {
            _uiState.value?.let {
                _uiState.postValue(
                    it.copy(
                        nameError = if (inputFieldEnum == InputFieldEnum.NAME) null else it.nameError,
                        emailError = if (inputFieldEnum == InputFieldEnum.EMAIL) null else it.emailError,
                        passwordError = if (inputFieldEnum == InputFieldEnum.PASSWORD) null else it.passwordError
                    )
                )
            }
        }
    }

    @StringRes
    private fun emptyFieldCheck(text: String): Int? =
        if (text.isBlank()) {
            R.string.signup_empty_field
        } else {
            null
        }

    @StringRes
    private fun emailFieldCheck(text: String) =
        emptyFieldCheck(text) ?: if (!text.contains(AT_SIGN)) {
            R.string.signup_not_e_mail
        } else {
            null
        }

    private suspend fun authorize(auth: Auth) {
        authRepository.insertAuth(auth)
    }
}