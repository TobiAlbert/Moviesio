package com.tobidaada.movieio.features.movies.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.tobidaada.movieio.features.movies.ResultWrapper
import com.tobidaada.movieio.features.movies.domain.repository.MovieRepository
import com.tobidaada.movieio.utils.Factory
import com.tobidaada.movieio.utils.MainCoroutineRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MovieRepositoryImplTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    // subject under test
    private lateinit var repo: MovieRepository

    // dependencies
    private val remoteDataSource: RemoteDataSource = mockk()
    private val localDataSource: LocalDataSource = mockk()
    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        repo = MovieRepositoryImpl(remoteDataSource, localDataSource, dispatcher)
    }

    @Test
    fun localDataSource_receivesProperParameters() = dispatcher.runBlockingTest {
        val slot = slot<Int>()

        coEvery { localDataSource.getMovie(capture(slot)) } returns null
        // when get movie called with the appropriate arguments
        val actualMovieId = 1
        repo.getMovie(actualMovieId)

        assertThat(slot.captured).isEqualTo(actualMovieId)
    }

    @Test
    fun getPopularMovies_savesFetchResult_whenFetchIsSuccessful() = dispatcher.runBlockingTest {
        coEvery { localDataSource.getPopularMovies() } returns flowOf(emptyList())
        coEvery { remoteDataSource.getPopularMovies() } returns ResultWrapper.Success(emptyList())

        repo.getPopularMovies().test {
            cancelAndConsumeRemainingEvents()
            coVerifyOrder {
                localDataSource.getPopularMovies()
                remoteDataSource.getPopularMovies()
                localDataSource.saveMovies(any())
            }
        }
    }

    @Test
    fun getPopularMovies_returnsErrorResponse_whenFetchIsNotSuccessful() = dispatcher.runBlockingTest {
        val movies = listOf(Factory.createMovieData())
        val errorMessage = "Error Fetching Remote Data"
        coEvery { localDataSource.getPopularMovies() } returns flowOf(movies)
        coEvery { remoteDataSource.getPopularMovies() } returns ResultWrapper.Error(errorMessage)

        repo.getPopularMovies().test {
            // item emitted by local data source
            awaitItem()

            val response = awaitItem()
            assertThat(response is ResultWrapper.Error).isTrue()

            coVerifyOrder {
                localDataSource.getPopularMovies()
                remoteDataSource.getPopularMovies()
                localDataSource.getPopularMovies()
            }

            cancelAndConsumeRemainingEvents()
        }
    }
}