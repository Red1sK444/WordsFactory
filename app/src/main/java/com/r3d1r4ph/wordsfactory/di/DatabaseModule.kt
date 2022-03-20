package com.r3d1r4ph.wordsfactory.di

import android.content.Context
import androidx.room.Room
import com.r3d1r4ph.wordsfactory.data.AppDatabase
import com.r3d1r4ph.wordsfactory.data.dictionary.DictionaryDao
import com.r3d1r4ph.wordsfactory.data.dictionary.MeaningDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideDictionaryDao(
        appDatabase: AppDatabase
    ): DictionaryDao = appDatabase.getDictionaryDao()

    @Provides
    fun provideMeaningDao(
        appDatabase: AppDatabase
    ): MeaningDao = appDatabase.getMeaningDao()

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext appContext: Context
    ): AppDatabase =
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
}