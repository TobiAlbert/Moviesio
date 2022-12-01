package com.tobidaada.movieio.features.movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tobidaada.movieio.features.movies.domain.models.Movie
import com.tobidaada.movieio.features.movies.domain.usecase.GetPopularMovies
import com.tobidaada.movieio.utils.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies,
    dispatchersProvider: DispatchersProvider,
) : ViewModel() {

    val state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())

    init {
        viewModelScope.launch(dispatchersProvider.main()) {
            getPopularMovies.invoke().collect { movies: List<Movie> ->
                state.value = state.value.copy(
                    isLoading = false,
                    movies = movies
                )
            }
        }
    }

    data class ViewState(
        val isLoading: Boolean = true,
        val movies: List<Movie> = emptyList(),
    )
}