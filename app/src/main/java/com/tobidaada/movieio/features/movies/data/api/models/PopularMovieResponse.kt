package com.tobidaada.movieio.features.movies.data.api.models

import com.google.gson.annotations.SerializedName

data class PopularMovieResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("results")
    val results: List<ApiMovie>,
)

data class MovieErrorResponse(
    @SerializedName("status_message")
    val message: String?
)