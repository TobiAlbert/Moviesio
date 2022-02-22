package com.tobidaada.movieio.features.movies.domain.usecase

import com.tobidaada.movieio.features.movies.domain.entities.Movie
import com.tobidaada.movieio.features.movies.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovie @Inject constructor(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(id: Int): Movie? = movieRepository.getMovie(id)
}