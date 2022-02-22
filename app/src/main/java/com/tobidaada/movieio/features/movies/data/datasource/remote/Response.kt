package com.tobidaada.movieio.features.movies.data.datasource.remote

import com.google.gson.annotations.SerializedName

data class PopularMovieResponse(
    @SerializedName("results")
    val results: List<MovieRemote>
)

data class MovieRemote(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("vote_average")
    val rating: Float,

    @SerializedName("backdrop_path")
    val backdropPath: String?
)

data class MovieErrorResponse(
    @SerializedName("status_message")
    val message: String?
)