package com.tobidaada.movieio.features.movies.data

import com.tobidaada.movieio.features.movies.data.api.MovieApi
import com.tobidaada.movieio.features.movies.data.api.models.ApiMovie
import com.tobidaada.movieio.features.movies.data.local.MovieDao
import com.tobidaada.movieio.features.movies.data.local.MovieLocal
import com.tobidaada.movieio.features.movies.domain.NetworkException
import com.tobidaada.movieio.features.movies.domain.models.Movie
import com.tobidaada.movieio.features.movies.domain.models.PaginatedMovies
import com.tobidaada.movieio.features.movies.domain.models.Pagination
import com.tobidaada.movieio.features.movies.domain.repository.MovieRepository
import com.tobidaada.movieio.utils.DispatchersProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class TmdbRepository @Inject constructor(
    private val api: MovieApi,
    private val dao: MovieDao,
    private val coroutineDispatcher: DispatchersProvider
) : MovieRepository {

    override suspend fun getPopularMovies(): Flow<List<Movie>> =
        withContext(coroutineDispatcher.io()) {
            return@withContext dao.getAllPopularMovies()
                .distinctUntilChanged()
                .map { localMovies -> localMovies.map(MovieLocal::toDomain) }
        }

    override suspend fun getMovie(id: Int): Movie? = withContext(coroutineDispatcher.io()) {
        return@withContext dao.getMovie(id)?.toDomain()
    }

    override suspend fun saveMovies(movies: List<Movie>) =
        dao.addMovies(movies.map(MovieLocal.Companion::fromDomain))

    override suspend fun requestMoreMovies(
        pageToLoad: Int,
    ): PaginatedMovies {
        try {
            val (page, totalPages, results) = api.getPopularMovies(page = pageToLoad)

            val pagination = Pagination(
                currentPage = page,
                totalPages = totalPages
            )

            return PaginatedMovies(
                movies = results.map(ApiMovie::mapToDomain),
                pagination = pagination
            )
        } catch (exception: HttpException) {
            throw NetworkException(exception.message ?: "Code: ${exception.code()}")
        }
    }
}