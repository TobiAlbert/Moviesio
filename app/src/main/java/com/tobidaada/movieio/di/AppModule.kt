package com.tobidaada.movieio.di

import com.tobidaada.movieio.utils.CoroutineDispatcherProvider
import com.tobidaada.movieio.utils.DispatchersProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindsMovieRepository(
        dispatchProvider: CoroutineDispatcherProvider
    ): DispatchersProvider
}