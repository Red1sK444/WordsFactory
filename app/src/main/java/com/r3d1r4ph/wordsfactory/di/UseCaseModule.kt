package com.r3d1r4ph.wordsfactory.di

import com.r3d1r4ph.wordsfactory.domain.usecases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindsCheckAuthUseCase(
        checkAuthUseCaseImpl: CheckAuthUseCaseImpl
    ): CheckAuthUseCase

    @Binds
    abstract fun bindsAuthUseCase(
        authUseCaseImpl: AuthUseCaseImpl
    ): AuthUseCase

    @Binds
    abstract fun bindsValidateInputFieldUseCase(
        validateInputFieldUseCaseImpl: ValidateInputFieldUseCaseImpl
    ): ValidateInputFieldUseCase

    @Binds
    abstract fun bindsCheckIsWordSavedUseCase(
        checkIsWordSavedUseCaseImpl: CheckIsWordSavedUseCaseImpl
    ): CheckIsWordSavedUseCase

    @Binds
    abstract fun bindsFindWordInDictionaryUseCase(
        findWordInDictionaryUseCaseImpl: FindWordInDictionaryUseCaseImpl
    ): FindWordInDictionaryUseCase

    @Binds
    abstract fun bindsSaveWordDictionaryUseCase(
        saveWordDictionaryUseCaseImpl: SaveWordDictionaryUseCaseImpl
    ): SaveWordDictionaryUseCase
}