package com.tobidaada.movieio.features.movies.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.tobidaada.movieio.features.movies.ResultWrapper
import com.tobidaada.movieio.features.movies.domain.entities.Movie
import com.tobidaada.movieio.features.movies.domain.usecase.GetMovie
import com.tobidaada.movieio.features.movies.domain.usecase.GetPopularMovies
import com.tobidaada.movieio.features.movies.presentation.models.GetMoviesUiState
import com.tobidaada.movieio.utils.getOrAwaitValue
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MovieViewModelTest {

    // dependencies
    private val getMovies: GetPopularMovies = mockk()
    private val getMovie: GetMovie = mockk()

    private val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun uiState_isSuccess_whenGetPopularMoviesReturnsSuccess() = dispatcher.runBlockingTest {
        // when a success response is returned from the use case
        val response = ResultWrapper.Success(emptyList<Movie>())
        coEvery { getMovies.invoke() } returns flowOf(response)

        val viewModel = MovieViewModel(getMovies, getMovie, dispatcher, dispatcher)

        // then the ui state should be in the success state
        viewModel.state.test {
            val uiState = awaitItem()
            assertTrue(
                "Expecting a UI State of type SuccessState. Found: ${uiState::class.java.simpleName}",
                uiState is GetMoviesUiState.SuccessState
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun uiState_isError_whenGetPopularMoviesReturnsError() = dispatcher.runBlockingTest {
        // when an error response is returned from the use case
        val response = ResultWrapper.Error("")
        coEvery { getMovies.invoke() } returns flowOf(response)

        val viewModel = MovieViewModel(getMovies, getMovie, dispatcher, dispatcher)

        // then the ui state should be in the error state
        viewModel.state.test {
            val uiState = awaitItem()
            assertTrue(
                "Expecting a UI State of type ErrorState. Found: ${uiState::class.java.simpleName}",
                uiState is GetMoviesUiState.ErrorState
            )
        }
    }

    @Test
    fun getMovieUseCase_receivesProperParameters() {
        // given a view model
        coEvery { getMovies.invoke() } returns flowOf(ResultWrapper.Success(emptyList()))
        val viewModel = MovieViewModel(getMovies, getMovie, dispatcher, dispatcher)

        // when get movie is called on the view model, passing in a movie id
        val slot = slot<Int>()
        coEvery { getMovie.invoke(capture(slot)) } returns null

        val actualMovieId = 1
        viewModel.getMovie(actualMovieId).getOrAwaitValue()

        // then the movie passed to the function should also be passed to the use case
        assertThat(slot.captured).isEqualTo(actualMovieId)
    }
}