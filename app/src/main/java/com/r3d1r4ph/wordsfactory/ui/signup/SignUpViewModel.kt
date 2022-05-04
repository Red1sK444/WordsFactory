package com.r3d1r4ph.wordsfactory.ui.signup

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.r3d1r4ph.wordsfactory.R
import com.r3d1r4ph.wordsfactory.domain.models.Auth
import com.r3d1r4ph.wordsfactory.domain.usecases.AuthUseCase
import com.r3d1r4ph.wordsfactory.domain.usecases.CheckAuthUseCase
import com.r3d1r4ph.wordsfactory.domain.usecases.IsEmailFieldUseCase
import com.r3d1r4ph.wordsfactory.domain.usecases.IsEmailFieldUseCaseImpl.Companion.AT_SIGN
import com.r3d1r4ph.wordsfactory.domain.usecases.IsFieldEmptyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val checkAuthUseCase: CheckAuthUseCase,
    private val authUseCase: AuthUseCase,
    private val isFieldEmptyUseCase: IsFieldEmptyUseCase,
    private val isEmailFieldUseCase: IsEmailFieldUseCase
) : ViewModel() {

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

            uiState.value?.let {
                _uiState.value = it.copy(
                    nameError = nameError,
                    emailError = emailError,
                    passwordError = passwordError
                )
            }
        }
    }

    fun dismissError(inputFieldEnum: InputFieldEnum) {
        viewModelScope.launch {
            _uiState.value?.let {
                _uiState.value =
                    it.copy(
                        nameError = if (inputFieldEnum == InputFieldEnum.NAME) null else it.nameError,
                        emailError = if (inputFieldEnum == InputFieldEnum.EMAIL) null else it.emailError,
                        passwordError = if (inputFieldEnum == InputFieldEnum.PASSWORD) null else it.passwordError
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
        authUseCase.execute(auth)
            .onSuccess { authorized ->
                uiState.value?.let {
                    if (authorized) {
                        _uiState.value = it.copy(
                            openDictionaryScreen = true
                        )
                    } else {

                    }
                }
            }
            .onFailure {

            }
    }
}