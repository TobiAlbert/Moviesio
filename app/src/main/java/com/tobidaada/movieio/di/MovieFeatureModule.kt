package com.tobidaada.movieio.di

import com.tobidaada.movieio.features.movies.data.datasource.local.LocalDataSourceImpl
import com.tobidaada.movieio.features.movies.data.datasource.remote.RemoteDataSourceImpl
import com.tobidaada.movieio.features.movies.data.repository.LocalDataSource
import com.tobidaada.movieio.features.movies.data.repository.MovieRepositoryImpl
import com.tobidaada.movieio.features.movies.data.repository.RemoteDataSource
import com.tobidaada.movieio.features.movies.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieFeatureModule {
    @Binds
    abstract fun bindsMovieRepository(
        movieRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository

    @Binds
    abstract fun bindsRemoteDataSource(
        remoteDataSource: RemoteDataSourceImpl
    ): RemoteDataSource

//    @Binds
//    abstract fun bindsLocalDataSource(
//        localDataSource: LocalDataSourceImpl
//    ): LocalDataSource
}