package com.tobidaada.movieio.features.movies.presentation.ui

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.tobidaada.movieio.R
import com.tobidaada.movieio.features.movies.domain.repository.MovieRepository
import com.tobidaada.movieio.features.movies.presentation.ui.adapters.MovieViewHolder
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
class MovieListFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var repo: MovieRepository

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun recyclerView_isVisible_whenDataIsReturned() = runBlockingTest {

        launchFragmentInHiltContainer<MovieListFragment>(null, R.style.Theme_Movieio)

        onView(withId(R.id.movieRv)).check(matches(isDisplayed()))
    }

    @Test
    fun clickMovie_navigatesToMovieFragment() = runBlockingTest {
        // launch fragment in activity
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchFragmentInHiltContainer<MovieListFragment>(Bundle(), R.style.Theme_Movieio) {
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(requireView(), navController)
        }

        // when the first item in the recycler view is clicked
        onView(withId(R.id.movieRv))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<MovieViewHolder>(
                    0,
                    click()
                )
            )

        // then the app should navigate to the MovieFragment screen with the appropriate argument
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.movieFragment)
    }
}