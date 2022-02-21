package com.tobidaada.movieio.features.movies.data.datasource.local

import com.tobidaada.movieio.features.movies.data.models.MovieData
import com.tobidaada.movieio.features.movies.data.repository.LocalDataSource
import com.tobidaada.movieio.features.movies.data.toDataObject
import com.tobidaada.movieio.features.movies.data.toLocalObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao
) : LocalDataSource {

    override fun getPopularMovies(): Flow<List<MovieData>> {
        return movieDao.getAllPopularMovies().map { movies: List<MovieLocal> ->
            movies.map { it.toDataObject() }
        }
    }

    override suspend fun getMovie(id: Int): MovieData? {
        return movieDao.getMovie(id)?.toDataObject()
    }

    override suspend fun saveMovies(movies: List<MovieData>) =
        movieDao.addMovies(movies.map { it.toLocalObject() })
}