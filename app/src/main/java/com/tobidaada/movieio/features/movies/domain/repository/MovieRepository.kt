package com.tobidaada.movieio.features.movies.domain.repository

import com.tobidaada.movieio.features.movies.ResultWrapper
import com.tobidaada.movieio.features.movies.domain.entities.Movie

interface MovieRepository {
    suspend fun getPopularMovies(): ResultWrapper<List<Movie>>
}