package com.tobidaada.movieio.features.movies.data.datasource.remote

import com.google.common.truth.Truth.assertThat
import com.tobidaada.movieio.features.movies.ResultWrapper
import com.tobidaada.movieio.utils.DEFAULT_ERROR_MESSAGE
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class RemoteDataSourceImplTest {

    // subject under test
    private lateinit var remoteDataSource: RemoteDataSourceImpl

    // dependencies
    private val movieService: MovieService = mockk()

    private val dispatcher = TestCoroutineDispatcher()


    @Before
    fun setup() {
        remoteDataSource = RemoteDataSourceImpl(movieService, dispatcher)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    @Test
    fun getPopularMovies_whenExceptionIsThrown_returnsErrorResult() = dispatcher.runBlockingTest {
        // given a service method that throws an exception when called
        val message = "io exception"
        coEvery { movieService.getPopularMovies() } throws IOException(message)

        // when called
        val response = remoteDataSource.getPopularMovies()

        // then the response should be an error with caught exception message
        assertThat(response is ResultWrapper.Error).isTrue()
        assertThat((response as? ResultWrapper.Error)?.message).isEqualTo(message)
    }

    @Test
    fun getPopularMovies_whenExceptionIsThrownWithoutErrorMessage_returnsDefaultErrorMessage() =
        dispatcher.runBlockingTest {
            // given a service that throws an exception when called but with no exception message
            coEvery { movieService.getPopularMovies() } throws Exception()

            // when the service method is called
            val response = remoteDataSource.getPopularMovies()

            // then the response should return the default exception message
            assertThat((response as? ResultWrapper.Error)?.message).isEqualTo(DEFAULT_ERROR_MESSAGE)
        }
}