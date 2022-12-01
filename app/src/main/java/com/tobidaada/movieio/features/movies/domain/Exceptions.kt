package com.tobidaada.movieio.features.movies.domain

class InvalidMovieException(message: String = "Invalid Movie"): Exception(message)

class NetworkException(message: String): Exception(message)

class NoMoreMoviesException(message: String = "No more movies"): Exception(message)