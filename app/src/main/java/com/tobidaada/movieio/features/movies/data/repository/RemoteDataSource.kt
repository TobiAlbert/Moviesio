package com.tobidaada.movieio.features.movies.data.repository

import com.tobidaada.movieio.features.movies.ResultWrapper
import com.tobidaada.movieio.features.movies.data.models.MovieData

interface RemoteDataSource {
    suspend fun getPopularMovies(): ResultWrapper<List<MovieData>>
}