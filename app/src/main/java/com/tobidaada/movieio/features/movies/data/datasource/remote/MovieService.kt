package com.tobidaada.movieio.features.movies.data.datasource.remote

import retrofit2.Response
import retrofit2.http.GET

interface MovieService {

    @GET("popular")
    suspend fun getPopularMovies(): Response<PopularMovieResponse>
}