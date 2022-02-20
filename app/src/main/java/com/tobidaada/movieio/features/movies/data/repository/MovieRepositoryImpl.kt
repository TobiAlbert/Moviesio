package com.tobidaada.movieio.features.movies.data.repository

import com.tobidaada.movieio.features.movies.ResultWrapper
import com.tobidaada.movieio.features.movies.data.toDomainObject
import com.tobidaada.movieio.features.movies.domain.entities.Movie
import com.tobidaada.movieio.features.movies.domain.repository.MovieRepository
import com.tobidaada.movieio.features.movies.transformResponse
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) : MovieRepository {

    override suspend fun getPopularMovies(): ResultWrapper<List<Movie>> {
        val response = remoteDataSource.getPopularMovies()
        return transformResponse(response) { movies -> movies.value.map { it.toDomainObject() } }
    }
}