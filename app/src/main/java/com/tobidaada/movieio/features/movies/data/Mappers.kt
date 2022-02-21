package com.tobidaada.movieio.features.movies.data

import com.tobidaada.movieio.features.movies.data.datasource.local.MovieLocal
import com.tobidaada.movieio.features.movies.data.datasource.remote.MovieRemote
import com.tobidaada.movieio.features.movies.data.models.MovieData
import com.tobidaada.movieio.features.movies.domain.entities.Movie

fun MovieRemote.toDataObject(): MovieData =
    MovieData(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        posterPath = posterPath,
        rating = rating,
        backdropPath = backdropPath,
    )

fun MovieData.toDomainObject(): Movie =
    Movie(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        posterPath = posterPath,
        rating = rating,
        backdropPath = backdropPath,
    )

fun MovieData.toLocalObject(): MovieLocal =
    MovieLocal(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        posterPath = posterPath,
        rating = rating,
        backdropPath = backdropPath,
    )

fun MovieLocal.toDataObject(): MovieData =
    MovieData(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        posterPath = posterPath,
        rating = rating,
        backdropPath = backdropPath,
    )