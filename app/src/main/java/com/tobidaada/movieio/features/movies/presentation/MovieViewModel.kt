package com.tobidaada.movieio.features.movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tobidaada.movieio.features.movies.domain.models.Movie
import com.tobidaada.movieio.features.movies.domain.usecase.GetMovie
import com.tobidaada.movieio.utils.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovie: GetMovie,
    private val dispatchersProvider: DispatchersProvider,
) : ViewModel() {

    val viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())

    data class ViewState(
        val isLoading: Boolean = true,
        val movie: Movie? = null,
    )

    fun onDisplayMovie(id: Int) =
        viewModelScope.launch(dispatchersProvider.main()) {

            val movie = getMovie(id = id)

            viewState.value = viewState.value.copy(
                isLoading = false,
                movie = movie
            )
        }
}