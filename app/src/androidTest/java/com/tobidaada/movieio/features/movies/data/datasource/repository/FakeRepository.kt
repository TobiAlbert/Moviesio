package com.tobidaada.movieio.features.movies.data.datasource.repository

import com.tobidaada.movieio.features.movies.domain.models.Movie
import com.tobidaada.movieio.features.movies.domain.models.PaginatedMovies
import com.tobidaada.movieio.features.movies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeRepository @Inject constructor(): MovieRepository {

    private val movies = mutableListOf(
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

    override suspend fun getPopularMovies(): Flow<List<Movie>> =
        flowOf(movies)

    override suspend fun getMovie(id: Int): Movie? = movies.firstOrNull { it.id == id }

    override suspend fun saveMovies(movies: List<Movie>) {
        this.movies += movies
    }

    override suspend fun requestMoreMovies(pageToLoad: Int): PaginatedMovies {
        TODO("Not yet implemented")
    }
}