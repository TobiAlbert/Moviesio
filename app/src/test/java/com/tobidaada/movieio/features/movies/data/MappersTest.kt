package com.tobidaada.movieio.features.movies.data

import com.google.common.truth.Truth.assertThat
import com.tobidaada.movieio.features.movies.data.datasource.local.MovieLocal
import com.tobidaada.movieio.features.movies.data.models.MovieData
import com.tobidaada.movieio.features.movies.domain.entities.Movie
import com.tobidaada.movieio.utils.Factory
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MappersTest {

    @Test
    fun `can map from MovieRemote to MovieData`() {
        // given a `MovieRemote` object
        val remote = Factory.createMovieRemote()

        // when mapped to a `MovieData` object
        val data = remote.toDataObject()
        val expected = MovieData(
            id = 1,
            title = "title",
            overview = "overview",
            releaseDate = "releaseDate",
            posterPath = "urlToPostPath",
            rating = 5.0F,
            backdropPath = "urlToBackDrop"
        )

        // then the mapped object should be equal (==) to a movie data object
        assertThat(data).isEqualTo(expected)
    }

    @Test
    fun `can map from MovieData to Movie`() {
        // given a `MovieData` object
        val data = Factory.createMovieData()

        // when mapped to a `Movie` object
        val domain = data.toDomainObject()
        val expected = Movie(
            id = 1,
            title = "title",
            overview = "overview",
            releaseDate = "releaseDate",
            posterPath = "urlToPostPath",
            rating = 5.0F,
            backdropPath = "urlToBackDrop"
        )

        // then the mapped object should be equal (==) to a movie data object
        assertThat(domain).isEqualTo(expected)
    }

    @Test
    fun `can map from MovieData to MovieLocal`() {
        // given a `MovieData` object
        val data = Factory.createMovieData()

        // when mapped to a `MovieLocal` object
        val local = data.toLocalObject()
        val expected = MovieLocal(
            id = 1,
            title = "title",
            overview = "overview",
            releaseDate = "releaseDate",
            posterPath = "urlToPostPath",
            rating = 5.0F,
            backdropPath = "urlToBackDrop"
        )

        // then the mapped object should be equal (==) to a movie data object
        assertThat(local).isEqualTo(expected)
    }

    @Test
    fun `can map from MovieLocal to MovieData`() {
        // given a `MovieLocal` object
        val local = Factory.createMovieLocal()

        // when mapped to a `MovieData` object
        val data = local.toDataObject()
        val expected = MovieData(
            id = 1,
            title = "title",
            overview = "overview",
            releaseDate = "releaseDate",
            posterPath = "urlToPostPath",
            rating = 5.0F,
            backdropPath = "urlToBackDrop"
        )

        // then the mapped object should be equal (==) to a movie data object
        assertThat(data).isEqualTo(expected)
    }
}