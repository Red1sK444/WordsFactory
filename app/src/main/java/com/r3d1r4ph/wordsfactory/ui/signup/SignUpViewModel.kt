package com.r3d1r4ph.wordsfactory.ui.signup

import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.r3d1r4ph.wordsfactory.R
import com.r3d1r4ph.wordsfactory.domain.exceptions.EmptyFieldException
import com.r3d1r4ph.wordsfactory.ui.utils.ExceptionHolder
import com.r3d1r4ph.wordsfactory.domain.exceptions.NoAtSignException
import com.r3d1r4ph.wordsfactory.domain.exceptions.NoAuthorizedException
import com.r3d1r4ph.wordsfactory.domain.models.Auth
import com.r3d1r4ph.wordsfactory.domain.usecases.AuthUseCase
import com.r3d1r4ph.wordsfactory.domain.usecases.ValidateInputFieldUseCase
import com.r3d1r4ph.wordsfactory.domain.validation.ValidationRule
import com.r3d1r4ph.wordsfactory.ui.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val validateInputFieldUseCase: ValidateInputFieldUseCase
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

            val nameResult =
                validateInputFieldUseCase.execute(ValidationRule.NotEmpty(input = name))
            val emailResult =
                validateInputFieldUseCase.execute(ValidationRule.IsEmail(input = email))
            val passwordResult =
                validateInputFieldUseCase.execute(ValidationRule.NotEmpty(input = password))

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
        enableAuthorizing()

        authUseCase.execute(auth)
            .onSuccess {
                _uiAction.value = Event(SignUpAction.OpenDictionaryScreen)
                disableAuthorizing()
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
                disableAuthorizing()
            }
    }

    private fun enableAuthorizing() {
        _uiState.value?.let {
            _uiState.value =
                it.copy(
                    authorizing = true
                )
        }
    }

    private fun disableAuthorizing() {
        _uiState.value?.let {
            _uiState.value =
                it.copy(
                    authorizing = false
                )
        }
    }
}