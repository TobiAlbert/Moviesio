package com.tobidaada.movieio.features.movies.data.repository

import com.tobidaada.movieio.features.movies.data.models.MovieData
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getPopularMovies(): Flow<List<MovieData>>
    suspend fun saveMovies(movies: List<MovieData>)
}