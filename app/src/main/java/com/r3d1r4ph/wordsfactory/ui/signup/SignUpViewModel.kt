package com.r3d1r4ph.wordsfactory.ui.signup

import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.r3d1r4ph.wordsfactory.R
import com.r3d1r4ph.wordsfactory.common.exceptions.EmptyFieldException
import com.r3d1r4ph.wordsfactory.common.exceptions.ExceptionHolder
import com.r3d1r4ph.wordsfactory.common.exceptions.NoAtSignException
import com.r3d1r4ph.wordsfactory.common.exceptions.NoAuthorizedException
import com.r3d1r4ph.wordsfactory.domain.models.Auth
import com.r3d1r4ph.wordsfactory.domain.usecases.*
import com.r3d1r4ph.wordsfactory.ui.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData(SignUpUiState())
    val uiState: LiveData<SignUpUiState>
        get() = _uiState

    private val _uiAction = MutableLiveData<Event<SignUpAction>>()
    val uiAction: LiveData<Event<SignUpAction>>
        get() = _uiAction.map { it }

    fun signUp(
        name: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {

            val nameResult = validateNameUseCase.execute(name)
            val emailResult = validateEmailUseCase.execute(email)
            val passwordResult = validatePasswordUseCase.execute(password)

            val hasError = listOf(
                nameResult,
                emailResult,
                passwordResult
            ).any { it.isFailure }

            if (hasError) {
                uiState.value?.let {
                    _uiState.value = it.copy(
                        nameError = getErrorMessageByValidationResult(nameResult),
                        emailError = getErrorMessageByValidationResult(emailResult),
                        passwordError = getErrorMessageByValidationResult(passwordResult)
                    )
                }
                return@launch
            }

            authorize(
                Auth(
                    name = name,
                    email = email,
                    password = password
                )
            )
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
    private fun getErrorMessageByValidationResult(validationResult: Result<Unit>): Int? {
        validationResult
            .onSuccess {
                return null
            }
            .onFailure {
                return when (it) {
                    is EmptyFieldException -> R.string.signup_empty_field
                    is NoAtSignException -> R.string.signup_not_e_mail
                    else -> R.string.unknown_exception
                }
            }

        return R.string.unknown_exception
    }

    private suspend fun authorize(auth: Auth) {
        authUseCase.execute(auth)
            .onSuccess {
                _uiAction.value = Event(SignUpAction.OpenDictionaryScreen)
            }
            .onFailure {
                _uiAction.value = Event(
                    SignUpAction.Error(
                        ExceptionHolder.Resource(
                            if (it is NoAuthorizedException) R.string.no_authorized_exception
                            else R.string.unknown_exception
                        )
                    )
                )
            }
    }
}