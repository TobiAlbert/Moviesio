package com.tobidaada.movieio.features.movies.data.repository

import com.tobidaada.movieio.di.IOCoroutineDispatcher
import com.tobidaada.movieio.features.movies.ResultWrapper
import com.tobidaada.movieio.features.movies.data.models.MovieData
import com.tobidaada.movieio.features.movies.data.toDomainObject
import com.tobidaada.movieio.features.movies.domain.entities.Movie
import com.tobidaada.movieio.features.movies.domain.repository.MovieRepository
import com.tobidaada.movieio.utils.networkBoundResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    @IOCoroutineDispatcher private val ioDispatcher: CoroutineDispatcher
) : MovieRepository {

    override suspend fun getMovie(id: Int): Movie? =
        withContext(ioDispatcher) { localDataSource.getMovie(id)?.toDomainObject() }

    override suspend fun getPopularMovies(): Flow<ResultWrapper<List<Movie>>> =
        networkBoundResource(
            query = { localDataSource.getPopularMovies() },
            fetch = { remoteDataSource.getPopularMovies() },
            isFetchSuccessful = ::isFetchSuccessful,
            getErrorMessage = ::getErrorMessage,
            saveFetchResult = ::saveFetchResult
        ).map(::mapToDomainModel)
        .flowOn(ioDispatcher)


    private fun mapToDomainModel(response: ResultWrapper<List<MovieData>>): ResultWrapper<List<Movie>> =
        when (response) {
            is ResultWrapper.Success -> ResultWrapper.Success(response.value.map { it.toDomainObject() })
            is ResultWrapper.Error -> response.copy()
        }

    private suspend fun saveFetchResult(result: ResultWrapper<List<MovieData>>): Unit =
        when (result) {
            is ResultWrapper.Success -> localDataSource.saveMovies(result.value)
            else -> Unit
        }

    private fun isFetchSuccessful(response: ResultWrapper<List<MovieData>>): Boolean = response is ResultWrapper.Success

    private fun getErrorMessage(response: ResultWrapper<List<MovieData>>): String =
        when (response) {
            is ResultWrapper.Error -> response.message
            else -> ""
        }
}