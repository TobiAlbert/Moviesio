package com.tobidaada.movieio.features.movies.presentation

import com.tobidaada.movieio.features.movies.domain.usecase.GetMovie
import com.tobidaada.movieio.utils.Factory
import com.tobidaada.movieio.utils.provideTestCoroutineDispatcher
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieViewModelTest {

    private val getMovie: GetMovie = mockk()

    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: MovieViewModel

    @Before
    fun setup() {
        viewModel = MovieViewModel(
            getMovie = getMovie,
            dispatchersProvider = provideTestCoroutineDispatcher(dispatcher)
        )
    }

    @Test
    fun uiState_isSuccess_whenGetPopularMoviesReturnsSuccess() = runTest {
        val movie = Factory.createMovieData()
        coEvery { getMovie.invoke(any()) } returns movie

        val expected = MovieViewModel.ViewState(
            isLoading = false,
            movie = movie
        )

        val movieId = 1

        viewModel.onDisplayMovie(id = movieId)

        assertEquals(
            expected,
            viewModel.viewState.value
        )
    }
}