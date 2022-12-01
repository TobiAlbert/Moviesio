package com.tobidaada.movieio.features.movies.data.api

import com.tobidaada.movieio.features.movies.data.api.models.PopularMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1
    ): PopularMovieResponse
}