package com.tobidaada.movieio.utils

import com.tobidaada.movieio.features.movies.domain.models.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher

object Factory {

    fun createMovieData(): Movie =
        Movie(
            id = 1,
            title = "title",
            overview = "overview",
            releaseDate = "releaseDate",
            posterPath = "urlToPostPath",
            rating = 5.0F,
            backdropPath = "urlToBackDrop"
        )

    fun createMovieDomain(): Movie =
        Movie(
            id = 1,
            title = "title",
            overview = "overview",
            releaseDate = "releaseDate",
            posterPath = "urlToPostPath",
            rating = 5.0F,
            backdropPath = "urlToBackDrop"
        )
}

@OptIn(ExperimentalCoroutinesApi::class)
fun provideTestCoroutineDispatcher(testCoroutineDispatcher: TestDispatcher): DispatchersProvider =
    object : DispatchersProvider {
        override fun io(): CoroutineDispatcher = testCoroutineDispatcher
        override fun main(): CoroutineDispatcher = testCoroutineDispatcher
    }