package com.tobidaada.movieio.features.movies.data.datasource.remote

import com.tobidaada.movieio.features.movies.ResultWrapper
import com.tobidaada.movieio.features.movies.data.models.MovieData
import com.tobidaada.movieio.features.movies.data.repository.RemoteDataSource
import com.tobidaada.movieio.features.movies.data.toDataObject
import com.tobidaada.movieio.features.movies.transformResponse
import com.tobidaada.movieio.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val movieService: MovieService): RemoteDataSource {

    override suspend fun getPopularMovies(): ResultWrapper<List<MovieData>> = withContext(Dispatchers.IO) {

        val response: ResultWrapper<PopularMovieResponse> = safeApiCall(Dispatchers.IO) { movieService.getPopularMovies() }

        return@withContext transformResponse(response) { success: ResultWrapper.Success<PopularMovieResponse> ->
            success.value.results.map { it.toDataObject() }
        }
    }
}