package com.tobidaada.movieio.features.movies.presentation.models

sealed class GetMoviesUiState<T> {
    data class SuccessState<T>(val data: T): GetMoviesUiState<T>()
    data class ErrorState<T>(val message: String): GetMoviesUiState<T>()
    class LoadingState<T>: GetMoviesUiState<T>()
}