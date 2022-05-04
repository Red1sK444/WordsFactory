package com.r3d1r4ph.wordsfactory.di

import com.r3d1r4ph.wordsfactory.domain.usecases.AuthUseCase
import com.r3d1r4ph.wordsfactory.domain.usecases.AuthUseCaseImpl
import com.r3d1r4ph.wordsfactory.domain.usecases.CheckAuthUseCase
import com.r3d1r4ph.wordsfactory.domain.usecases.CheckAuthUseCaseImpl
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
}