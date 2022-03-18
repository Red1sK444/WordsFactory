package com.r3d1r4ph.wordsfactory.di

import com.r3d1r4ph.wordsfactory.data.dictionary.DictionaryRepository
import com.r3d1r4ph.wordsfactory.data.dictionary.DictionaryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindDictionaryRepository(
        dictionaryRepositoryImpl: DictionaryRepositoryImpl
    ): DictionaryRepository
}