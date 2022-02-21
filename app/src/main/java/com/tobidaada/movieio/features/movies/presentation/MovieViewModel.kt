package com.tobidaada.movieio.features.movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.tobidaada.movieio.features.movies.ResultWrapper
import com.tobidaada.movieio.features.movies.domain.entities.Movie
import com.tobidaada.movieio.features.movies.domain.usecase.GetMovie
import com.tobidaada.movieio.features.movies.domain.usecase.GetPopularMovies
import com.tobidaada.movieio.features.movies.presentation.models.GetMoviesUiState
import com.tobidaada.movieio.utils.AppDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMovies,
    private val getMovieUseCase: GetMovie,
    private val dispatchers: AppDispatchers
): ViewModel() {

    private val _state: MutableStateFlow<GetMoviesUiState<List<Movie>>> =
        MutableStateFlow(GetMoviesUiState.SuccessState(emptyList()))

    val state: StateFlow<GetMoviesUiState<List<Movie>>> = _state.asStateFlow()

    init {
        // get movies
        viewModelScope.launch {
            _state.value = GetMoviesUiState.LoadingState()

            getPopularMoviesUseCase.invoke().collect { result: ResultWrapper<List<Movie>> ->
                _state.value = when (result) {
                    is ResultWrapper.Success -> GetMoviesUiState.SuccessState(result.value)
                    is ResultWrapper.Error -> GetMoviesUiState.ErrorState(result.message)
                }
            }
        }
    }

    fun getMovie(id: Int) = liveData(dispatchers.io()) {
        emit(getMovieUseCase(id))
    }
}