package com.tobidaada.movieio.utils

import com.tobidaada.movieio.features.movies.data.datasource.local.MovieLocal
import com.tobidaada.movieio.features.movies.data.datasource.remote.MovieRemote
import com.tobidaada.movieio.features.movies.data.models.MovieData
import com.tobidaada.movieio.features.movies.domain.entities.Movie

object Factory {

    fun createMovieData(): MovieData =
        MovieData(
            id = 1,
            title = "title",
            overview = "overview",
            releaseDate = "releaseDate",
            posterPath = "urlToPostPath",
            rating = 5.0F,
            backdropPath = "urlToBackDrop"
        )

    fun createMovieRemote(): MovieRemote =
        MovieRemote(
            id = 1,
            title = "title",
            overview = "overview",
            releaseDate = "releaseDate",
            posterPath = "urlToPostPath",
            rating = 5.0F,
            backdropPath = "urlToBackDrop"
        )

    fun createMovieLocal(): MovieLocal =
        MovieLocal(
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