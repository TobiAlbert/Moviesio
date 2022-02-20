package com.tobidaada.movieio.features.movies.domain.repository

import com.tobidaada.movieio.features.movies.ResultWrapper
import com.tobidaada.movieio.features.movies.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getPopularMovies(): Flow<ResultWrapper<List<Movie>>>
}