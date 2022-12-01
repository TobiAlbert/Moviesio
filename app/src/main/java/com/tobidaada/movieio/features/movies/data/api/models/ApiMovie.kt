package com.tobidaada.movieio.features.movies.data.api.models

import com.google.gson.annotations.SerializedName
import com.tobidaada.movieio.features.movies.domain.InvalidMovieException
import com.tobidaada.movieio.features.movies.domain.models.Movie

data class ApiMovie(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("overview")
    val overview: String?,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("vote_average")
    val rating: Float?,

    @SerializedName("backdrop_path")
    val backdropPath: String?,
) {
    fun mapToDomain(): Movie =
        Movie(
            id = id ?: throw InvalidMovieException(),
            title = title.orEmpty(),
            overview = overview.orEmpty(),
            releaseDate = releaseDate.orEmpty(),
            posterPath = posterPath.orEmpty(),
            rating = -1F,
            backdropPath = backdropPath.orEmpty()
        )
}