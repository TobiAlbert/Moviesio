package com.tobidaada.movieio.features.movies.data.datasource.repository

import com.tobidaada.movieio.features.movies.ResultWrapper
import com.tobidaada.movieio.features.movies.domain.entities.Movie
import com.tobidaada.movieio.features.movies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeRepository @Inject constructor(): MovieRepository {

    private val movies = listOf(
        Movie(
            id = 1,
            title = "title",
            overview = "overview",
            releaseDate = "releaseDate",
            posterPath = "urlToPostPath",
            rating = 5.0F,
            backdropPath = "urlToBackDrop"
        )
    )

    override suspend fun getPopularMovies(): Flow<ResultWrapper<List<Movie>>> =
        flowOf(ResultWrapper.Success(movies))

    override suspend fun getMovie(id: Int): Movie? = movies.firstOrNull { it.id == id }
}