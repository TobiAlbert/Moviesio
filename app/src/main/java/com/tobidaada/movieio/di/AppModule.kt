package com.tobidaada.movieio.di

import com.tobidaada.movieio.utils.AppDispatchers
import com.tobidaada.movieio.utils.AppDispatchersImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindsAppDispatchers(
        appDispatchersImpl: AppDispatchersImpl
    ): AppDispatchers
}