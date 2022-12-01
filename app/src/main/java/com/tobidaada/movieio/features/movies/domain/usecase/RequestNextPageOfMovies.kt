package com.tobidaada.movieio.features.movies.domain.usecase

import com.tobidaada.movieio.features.movies.domain.NoMoreMoviesException
import com.tobidaada.movieio.features.movies.domain.models.Pagination
import com.tobidaada.movieio.features.movies.domain.repository.MovieRepository
import com.tobidaada.movieio.utils.DispatchersProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RequestNextPageOfMovies @Inject constructor(
    private val movieRepository: MovieRepository,
    private val dispatchersProvider: DispatchersProvider
) {
    suspend operator fun invoke(pageToLoad: Int): Pagination = withContext(dispatchersProvider.io()) {
        val (movies, pagination) = movieRepository.requestMoreMovies(pageToLoad)

        if (movies.isEmpty()) {
            throw NoMoreMoviesException()
        }

        movieRepository.saveMovies(movies)

        return@withContext pagination
    }
}