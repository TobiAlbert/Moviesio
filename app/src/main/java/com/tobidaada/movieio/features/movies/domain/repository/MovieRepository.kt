package com.tobidaada.movieio.features.movies.domain.repository

import com.tobidaada.movieio.features.movies.domain.models.Movie
import com.tobidaada.movieio.features.movies.domain.models.PaginatedMovies
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getPopularMovies(): Flow<List<Movie>>
    suspend fun getMovie(id: Int): Movie?
    suspend fun saveMovies(movies: List<Movie>)
    suspend fun requestMoreMovies(pageToLoad: Int): PaginatedMovies
}