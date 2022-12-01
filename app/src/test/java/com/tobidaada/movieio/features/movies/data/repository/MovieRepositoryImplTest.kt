package com.tobidaada.movieio.features.movies.data.repository

import com.tobidaada.movieio.features.movies.data.TmdbRepository
import com.tobidaada.movieio.features.movies.data.api.MovieApi
import com.tobidaada.movieio.features.movies.data.local.MovieDao
import com.tobidaada.movieio.features.movies.domain.repository.MovieRepository
import com.tobidaada.movieio.utils.provideTestCoroutineDispatcher
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before

@ExperimentalCoroutinesApi
class MovieRepositoryImplTest {

    // subject under test
    private lateinit var repo: MovieRepository

    // dependencies
    private val api: MovieApi = mockk()
    private val dao: MovieDao = mockk()
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        repo = TmdbRepository(
            api = api,
            dao = dao,
            coroutineDispatcher = provideTestCoroutineDispatcher(dispatcher)
        )
    }
}