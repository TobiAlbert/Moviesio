package com.tobidaada.movieio.features.movies.presentation

import com.tobidaada.movieio.features.movies.domain.models.Movie
import com.tobidaada.movieio.features.movies.domain.usecase.GetPopularMovies
import com.tobidaada.movieio.utils.provideTestCoroutineDispatcher
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class MovieListViewModelTest {

    private val getMovies: GetPopularMovies = mockk()
    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: MovieListViewModel

    @Test
    fun `view state when popular movies is returned`() = runTest {
        val movies: List<Movie> = mockk()
        coEvery { getMovies() } returns flowOf(movies)

        viewModel = MovieListViewModel(
            getPopularMovies = getMovies,
            dispatchersProvider = provideTestCoroutineDispatcher(dispatcher)
        )

        val expected = MovieListViewModel.ViewState(
            isLoading = false,
            movies = movies
        )

        assertEquals(expected, viewModel.state.value)
    }

    @Test
    fun `view state when no popular movie is returned`() {
        val movies = emptyList<Movie>()
        coEvery { getMovies() } returns flowOf(movies)

        viewModel = MovieListViewModel(
            getPopularMovies = getMovies,
            dispatchersProvider = provideTestCoroutineDispatcher(dispatcher)
        )

        val expected = MovieListViewModel.ViewState(
            isLoading = false,
            movies = movies
        )

        assertEquals(expected, viewModel.state.value)
    }
}