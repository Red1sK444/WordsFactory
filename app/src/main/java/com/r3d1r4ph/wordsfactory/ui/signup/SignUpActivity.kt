package com.r3d1r4ph.wordsfactory.ui.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.textfield.TextInputLayout
import com.r3d1r4ph.wordsfactory.R
import com.r3d1r4ph.wordsfactory.common.exceptions.ExceptionHolder
import com.r3d1r4ph.wordsfactory.databinding.ActivitySignUpBinding
import com.r3d1r4ph.wordsfactory.ui.menu.MenuActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private val viewBinding by viewBinding(ActivitySignUpBinding::bind, R.id.rootLayout)
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setObservers()
        initView()
    }

    private fun setObservers() = with(viewModel) {
        uiState.observe(this@SignUpActivity) { uiState ->
            viewBinding.signUpButton.isEnabled = !uiState.authorizing
            setErrors(uiState.nameError, uiState.emailError, uiState.passwordError)
        }
        uiAction.observe(this@SignUpActivity) { event ->
            event.getContentIfNotHandled()?.let { action ->
                when (action) {
                    is SignUpAction.OpenDictionaryScreen -> openDictionaryScreen()
                    is SignUpAction.Error -> {
                        val errorMessage = when (val holder = action.exceptionHolder) {
                            is ExceptionHolder.Server -> holder.message
                            is ExceptionHolder.Resource -> getString(holder.messageId)
                        }

                        Toast.makeText(this@SignUpActivity, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun initView() {
        setErrorDismisses()

        viewBinding.signUpButton.setOnClickListener {
            with(viewBinding) {
                viewModel.signUp(
                    name = signUpNameTextInputEditText.text.toString(),
                    email = signUpEmailTextInputEditText.text.toString(),
                    password = signUpPasswordTextInputEditText.text.toString()
                )
            }
        }
    }

    private fun setErrorDismisses() = with(viewBinding) {
        signUpNameTextInputEditText.addTextChangedListener {
            viewModel.dismissError(InputFieldEnum.NAME)
        }

        signUpEmailTextInputEditText.addTextChangedListener {
            viewModel.dismissError(InputFieldEnum.EMAIL)
        }

        signUpPasswordTextInputEditText.addTextChangedListener {
            viewModel.dismissError(InputFieldEnum.PASSWORD)
        }
    }

    private fun setErrors(
        @StringRes nameError: Int?,
        @StringRes emailError: Int?,
        @StringRes passwordError: Int?
    ) = with(viewBinding) {
        signUpNameTextInputLayout.setError(nameError)
        signUpEmailTextInputLayout.setError(emailError)
        signUpPasswordTextInputLayout.setError(passwordError)
    }

    private fun TextInputLayout.setError(@StringRes errorId: Int?) {
        error = resources.getString(errorId ?: R.string.empty)
        isErrorEnabled = errorId != null
    }

    private fun openDictionaryScreen() {
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
    }
}