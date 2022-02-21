package com.tobidaada.movieio.features.movies.data.models

data class MovieData(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val posterPath: String,
    val rating: Float,
)