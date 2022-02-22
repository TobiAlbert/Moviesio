package com.tobidaada.movieio.di

import com.tobidaada.movieio.features.movies.data.datasource.repository.FakeRepository
import com.tobidaada.movieio.features.movies.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [MovieRepositoryModule::class]
)
abstract class TestMovieRepositoryModule {

    @Binds
    abstract fun bindsMovieRepository(
        fakeRepository: FakeRepository
    ): MovieRepository
}