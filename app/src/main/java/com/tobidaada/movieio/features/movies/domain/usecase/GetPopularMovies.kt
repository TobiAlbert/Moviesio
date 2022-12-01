package com.tobidaada.movieio.features.movies.domain.usecase

import com.tobidaada.movieio.features.movies.domain.models.Movie
import com.tobidaada.movieio.features.movies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMovies @Inject constructor(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(): Flow<List<Movie>> =
        movieRepository.getPopularMovies()
}