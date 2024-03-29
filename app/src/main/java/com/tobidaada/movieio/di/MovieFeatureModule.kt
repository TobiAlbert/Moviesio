package com.tobidaada.movieio.di

import com.tobidaada.movieio.features.movies.data.TmdbRepository
import com.tobidaada.movieio.features.movies.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieRepositoryModule {
    @Binds
    abstract fun bindsMovieRepository(
        movieRepositoryImpl: TmdbRepository
    ): MovieRepository
}