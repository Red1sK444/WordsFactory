package com.r3d1r4ph.wordsfactory.ui.signup

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.textfield.TextInputLayout
import com.r3d1r4ph.wordsfactory.R
import com.r3d1r4ph.wordsfactory.databinding.ActivitySignUpBinding
import com.r3d1r4ph.wordsfactory.domain.Auth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private val viewBinding by viewBinding(ActivitySignUpBinding::bind, R.id.rootLayout)
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        viewModel.checkAuth()
        initView()
    }

    private fun initView() {
        viewModel.uiState.observe(this) { uiState ->
            viewBinding.signUpButton.isEnabled = !uiState.openDictionaryScreen
            if (uiState.openDictionaryScreen) {
                openDictionaryScreen()
            } else {
                setErrors(uiState.nameError, uiState.emailError, uiState.passwordError)
            }
        }

        setErrorDismisses()

        viewBinding.signUpButton.setOnClickListener {
            with(viewBinding) {
                viewModel.signUp(
                    Auth(
                        name = signUpNameTextInputEditText.text.toString(),
                        email = signUpEmailTextInputEditText.text.toString(),
                        password = signUpPasswordTextInputEditText.text.toString()
                    )
                )
            }
        }
    }

    private fun setErrorDismisses() = with(viewBinding) {
        signUpNameTextInputEditText.addTextChangedListener {
            setErrorDismiss(signUpNameTextInputLayout)
        }

        signUpEmailTextInputEditText.addTextChangedListener {
            setErrorDismiss(signUpEmailTextInputLayout)
        }

        signUpPasswordTextInputEditText.addTextChangedListener {
            setErrorDismiss(signUpPasswordTextInputLayout)
        }
    }

    private fun setErrorDismiss(textInputLayout: TextInputLayout) {
        textInputLayout.error = resources.getString(R.string.empty)
        textInputLayout.isErrorEnabled = false
    }

    private fun setErrors(
        @StringRes nameError: Int,
        @StringRes emailError: Int,
        @StringRes passwordError: Int
    ) = with(viewBinding) {

        setError(signUpNameTextInputLayout, nameError)
        setError(signUpEmailTextInputLayout, emailError)
        setError(signUpPasswordTextInputLayout, passwordError)
    }

    private fun setError(textInputLayout: TextInputLayout, @StringRes errorId: Int) {
        with(textInputLayout) {
            error = resources.getString(errorId)
            isErrorEnabled = errorId != R.string.empty
        }
    }

    private fun openDictionaryScreen() {
        Toast.makeText(this, "openDictionaryScreen", Toast.LENGTH_SHORT).show()
        //TODO openDictionaryScreen
    }
}