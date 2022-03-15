package com.r3d1r4ph.wordsfactory.data

import com.r3d1r4ph.wordsfactory.data.intro.IntroDataSource
import com.r3d1r4ph.wordsfactory.data.intro.IntroDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Singleton
    @Provides
    fun provideIntroDataSource(): IntroDataSource = IntroDataSourceImpl()
}