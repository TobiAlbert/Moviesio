package com.tobidaada.movieio.features.movies

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class Error(val message: String): ResultWrapper<Nothing>()
}

fun <T, R> transformResponse(response: ResultWrapper<R>, block: (ResultWrapper.Success<R>) -> T): ResultWrapper<T> {
    return when (response) {
        is ResultWrapper.Success -> ResultWrapper.Success(block(response))
        is ResultWrapper.Error -> response
    }
}