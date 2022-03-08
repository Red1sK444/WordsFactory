package com.r3d1r4ph.wordsfactory.ui.signup

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.r3d1r4ph.wordsfactory.R
import com.r3d1r4ph.wordsfactory.databinding.ActivitySignUpBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {

    private val viewBinding by viewBinding(ActivitySignUpBinding::bind, R.id.rootLayout)
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        setErrorDismisses()
        setOnClickListener()
        setCollector()
    }

    private fun setErrorDismisses() = with(viewBinding) {
        signUpNameTextInputEditText.addTextChangedListener {
            signUpNameTextInputLayout.error = resources.getString(R.string.empty)
        }

        signUpEmailTextInputEditText.addTextChangedListener {
            signUpEmailTextInputLayout.error = resources.getString(R.string.empty)
        }

        signUpPasswordTextInputEditText.addTextChangedListener {
            signUpPasswordTextInputLayout.error = resources.getString(R.string.empty)
        }
    }

    private fun setOnClickListener() {
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

    private fun setCollector() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    if (uiState.openDictionaryScreen) {
                        openDictionaryScreen()
                    } else {
                        setErrors(uiState.nameError, uiState.emailError, uiState.passwordError)
                    }
                }
            }
        }
    }

    private fun setErrors(
        @StringRes nameError: Int,
        @StringRes emailError: Int,
        @StringRes passwordError: Int
    ) = with(viewBinding) {
        signUpNameTextInputLayout.error =
            resources.getString(nameError)
        signUpEmailTextInputLayout.error =
            resources.getString(emailError)
        signUpPasswordTextInputLayout.error =
            resources.getString(passwordError)
    }

    private fun openDictionaryScreen() {
        Toast.makeText(this, "openDictionaryScreen", Toast.LENGTH_SHORT).show()
    }
}