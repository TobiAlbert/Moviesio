package com.tobidaada.movieio.features.movies.domain.models

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val posterPath: String,
    val rating: Float,
    val backdropPath: String?
)