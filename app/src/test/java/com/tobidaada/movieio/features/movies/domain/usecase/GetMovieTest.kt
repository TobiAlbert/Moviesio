package com.tobidaada.movieio.features.movies.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.tobidaada.movieio.features.movies.domain.repository.MovieRepository
import com.tobidaada.movieio.utils.Factory
import com.tobidaada.movieio.utils.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class GetMovieTest {
    // dependencies
    private val repo: MovieRepository = mockk()

    // subject under test
    private val getMovieUseCase = GetMovie(repo)

    private val dispatcher = MainCoroutineRule()

    @Test
    fun getMovie_returnsNull_whenMovieWithIdDoesNotExist() = dispatcher.runBlockingTest {
        // given a use case that returns null when an id is passed
        coEvery { getMovieUseCase.invoke(any()) } returns null

        // when the use case is invoked
        val movieId = 1
        val movie = getMovieUseCase.invoke(movieId)

        // the returned movie object should be null
        assertThat(movie).isNull()
    }

    @Test
    fun getMovie_returnsMovie_whenMovieWithIdExists() = dispatcher.runBlockingTest {
        // given a use case that returns a movie when an id is passed
        val dummyResponse = Factory.createMovieDomain()
        coEvery { getMovieUseCase.invoke(any()) } returns dummyResponse

        // when the use case is invoked
        val movieId = 1
        val movie = getMovieUseCase.invoke(movieId)

        // the returned movie object should not be null
        assertThat(movie).isNotNull()
        assertThat(movie == dummyResponse)
    }
}