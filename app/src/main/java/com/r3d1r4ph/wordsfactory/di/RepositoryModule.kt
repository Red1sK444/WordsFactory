package com.r3d1r4ph.wordsfactory.di

import com.r3d1r4ph.wordsfactory.domain.interfaces.DictionaryRepository
import com.r3d1r4ph.wordsfactory.data.dictionary.DictionaryRepositoryImpl
import com.r3d1r4ph.wordsfactory.domain.interfaces.AuthRepository
import com.r3d1r4ph.wordsfactory.data.auth.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindDictionaryRepository(
        dictionaryRepositoryImpl: DictionaryRepositoryImpl
    ): DictionaryRepository

    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}