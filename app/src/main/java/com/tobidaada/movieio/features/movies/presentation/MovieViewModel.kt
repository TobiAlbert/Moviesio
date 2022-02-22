package com.tobidaada.movieio.features.movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.tobidaada.movieio.di.IOCoroutineDispatcher
import com.tobidaada.movieio.di.MainCoroutineDispatcher
import com.tobidaada.movieio.features.movies.ResultWrapper
import com.tobidaada.movieio.features.movies.domain.entities.Movie
import com.tobidaada.movieio.features.movies.domain.usecase.GetMovie
import com.tobidaada.movieio.features.movies.domain.usecase.GetPopularMovies
import com.tobidaada.movieio.features.movies.presentation.models.GetMoviesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMovies,
    private val getMovieUseCase: GetMovie,
    @IOCoroutineDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainCoroutineDispatcher private val mainDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _state: MutableStateFlow<GetMoviesUiState<List<Movie>>> =
        MutableStateFlow(GetMoviesUiState.SuccessState(emptyList()))

    val state: StateFlow<GetMoviesUiState<List<Movie>>> = _state.asStateFlow()

    init {
        // get movies
        viewModelScope.launch(mainDispatcher) {
            _state.value = GetMoviesUiState.LoadingState()

            getPopularMoviesUseCase.invoke().collect { result: ResultWrapper<List<Movie>> ->
                _state.value = when (result) {
                    is ResultWrapper.Success -> GetMoviesUiState.SuccessState(result.value)
                    is ResultWrapper.Error -> GetMoviesUiState.ErrorState(result.message)
                }
            }
        }
    }

    fun getMovie(id: Int) = liveData(ioDispatcher) {
        emit(getMovieUseCase(id))
    }
}