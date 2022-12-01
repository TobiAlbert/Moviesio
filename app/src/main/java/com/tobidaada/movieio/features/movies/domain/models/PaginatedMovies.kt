package com.tobidaada.movieio.features.movies.domain.models

data class PaginatedMovies(
    val movies: List<Movie>,
    val pagination: Pagination
)