package com.r3d1r4ph.wordsfactory.di

import com.r3d1r4ph.wordsfactory.domain.usecases.CheckAuthUseCase
import com.r3d1r4ph.wordsfactory.domain.usecases.CheckAuthUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindsCheckAuthUseCase(
        checkAuthUseCaseImpl: CheckAuthUseCaseImpl
    ) : CheckAuthUseCase
}