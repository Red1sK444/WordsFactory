package com.r3d1r4ph.wordsfactory.ui.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.textfield.TextInputLayout
import com.r3d1r4ph.wordsfactory.R
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
        observeViewModel()
        initView()
    }

    private fun observeViewModel() {
        viewModel.uiState.observe(this) { uiState ->
            viewBinding.signUpButton.isEnabled = !uiState.openDictionaryScreen
            if (uiState.openDictionaryScreen) {
                openDictionaryScreen()
            } else {
                setErrors(uiState.nameError, uiState.emailError, uiState.passwordError)
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
        setError(signUpNameTextInputLayout, nameError)
        setError(signUpEmailTextInputLayout, emailError)
        setError(signUpPasswordTextInputLayout, passwordError)
    }

    private fun setError(textInputLayout: TextInputLayout, @StringRes errorId: Int?) {
        with(textInputLayout) {
            error = resources.getString(errorId ?: R.string.empty)
            isErrorEnabled = errorId != null
        }
    }

    private fun openDictionaryScreen() {
        startActivity(Intent(this, MenuActivity::class.java))
    }
}