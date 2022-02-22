package com.tobidaada.movieio.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object AppModuleProvides {

    @IOCoroutineDispatcher
    @Provides
    fun provideIoCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainCoroutineDispatcher
    @Provides
    fun provideMainCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Main
}