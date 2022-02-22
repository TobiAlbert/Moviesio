package com.tobidaada.movieio.features.movies.presentation.ui

import androidx.core.os.bundleOf
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.tobidaada.movieio.R
import com.tobidaada.movieio.features.movies.domain.repository.MovieRepository
import com.tobidaada.movieio.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MovieFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repo: MovieRepository

    @Before
    fun setup() {
        hiltRule.inject()

        val bundle = bundleOf("movieId" to 1)
        launchFragmentInHiltContainer<MovieFragment>(bundle, R.style.Theme_Movieio)
    }

    @Test
    fun textViews_areVisible() {
        onView(withId(R.id.movieTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.movieYear)).check(matches(isDisplayed()))
        onView(withId(R.id.movieSummary)).check(matches(isDisplayed()))
        onView(withId(R.id.showStatus)).check(matches(isDisplayed()))
    }

    @Test
    fun textViews_displayProperMovieTexts() = runBlockingTest {
        val movie = repo.getMovie(1)

        onView(withId(R.id.movieTitle)).check(matches(withText(movie?.title)))
        onView(withId(R.id.movieYear)).check(matches(withText(movie?.releaseDate)))
        onView(withId(R.id.movieSummary)).check(matches(withText(movie?.overview)))
        onView(withId(R.id.showStatus)).check(matches(withText("Released")))
    }
}