package com.r3d1r4ph.wordsfactory.di

import com.r3d1r4ph.wordsfactory.domain.usecases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindsCheckAuthUseCase(
        checkAuthUseCaseImpl: CheckAuthUseCaseImpl
    ) : CheckAuthUseCase

    @Binds
    abstract fun bindsAuthUseCase(
        authUseCaseImpl: AuthUseCaseImpl
    ) : AuthUseCase

    @Binds
    abstract fun bindsValidateNameUseCase(
        validateNameUseCaseImpl: ValidateNameUseCaseImpl
    ) : ValidateNameUseCase

    @Binds
    abstract fun bindsValidatePasswordUseCase(
        validatePasswordUseCaseImpl: ValidatePasswordUseCaseImpl
    ) : ValidatePasswordUseCase

    @Binds
    abstract fun bindsValidateEmailUseCase(
        validateEmailUseCaseImpl: ValidateEmailUseCaseImpl
    ) : ValidateEmailUseCase
}