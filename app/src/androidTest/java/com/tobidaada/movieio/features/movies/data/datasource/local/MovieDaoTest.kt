package com.tobidaada.movieio.features.movies.data.datasource.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.tobidaada.movieio.features.movies.data.local.AppDatabase
import com.tobidaada.movieio.features.movies.data.local.MovieDao
import com.tobidaada.movieio.features.movies.data.local.MovieLocal
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@SmallTest
@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: MovieDao

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.movieDao()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun canInsertAndRetrieveMovie() = runBlockingTest {
        // given a movie object
        val movieId = 1
        val movie = createMovie()

        // when inserted into the db, and retrieved by its id
        dao.addMovies(listOf(movie))
        val retrievedMovie = dao.getMovie(movieId)

        // then the retrieved should match
        assertThat(retrievedMovie).isEqualTo(movie)
    }

    @Test
    fun canInsertAndReplaceMovie() = runBlockingTest {
        val movieId = 1
        val movie1 = createMovie(movieId)

        val movie2 = movie1.copy(id = 2)

        dao.addMovies(listOf(movie1, movie2))

        val movie3 =
            MovieLocal(
                id = movieId,
                title = "title2",
                overview = "overview2",
                releaseDate = "releaseDate2",
                posterPath = "urlToPostPath2",
                rating = 2.0F,
                backdropPath = "urlToBackDrop2"
            )

        dao.addMovies(listOf(movie3))

        val retrievedMovie = dao.getMovie(movieId)
        assertThat(retrievedMovie).isEqualTo(movie3)
    }

    @Test
    fun canDeleteMoviesFromDatabase() = runBlockingTest {
        val startIndex = 1
        val endIndex = 10

        val movies = (startIndex..endIndex).map(::createMovie)

        dao.addMovies(movies)

        dao.deleteAllMovies()

        dao.getAllPopularMovies().test {
            val localMovies = awaitItem()
            assertThat(localMovies).isEmpty()
        }
    }

    private fun createMovie(movieId: Int = 1): MovieLocal =
        MovieLocal(
            id = movieId,
            title = "title",
            overview = "overview",
            releaseDate = "releaseDate",
            posterPath = "urlToPostPath",
            rating = 5.0F,
            backdropPath = "urlToBackDrop"
        )
}