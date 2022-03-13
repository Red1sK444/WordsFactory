package com.r3d1r4ph.wordsfactory.ui.signup

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.r3d1r4ph.wordsfactory.data.Database
import com.r3d1r4ph.wordsfactory.data.auth.AuthRepositoryImpl

class SignUpViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SignUpViewModel(
            authRepository = AuthRepositoryImpl(
                authDao = Database.getInstance(application).getAuthDao()
            )
        ) as T
    }
}