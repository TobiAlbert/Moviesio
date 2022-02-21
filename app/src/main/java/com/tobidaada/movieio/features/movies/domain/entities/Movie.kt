package com.tobidaada.movieio.features.movies.domain.entities

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val posterPath: String,
    val rating: Float,
)