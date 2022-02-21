package com.tobidaada.movieio.features.movies.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.tobidaada.movieio.R
import com.tobidaada.movieio.databinding.FragmentMovieListBinding
import com.tobidaada.movieio.features.movies.presentation.MovieViewModel
import com.tobidaada.movieio.features.movies.presentation.models.GetMoviesUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private val movieViewModel: MovieViewModel by viewModels()
    private lateinit var binding: FragmentMovieListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                movieViewModel.state.collect {
                    val message = when(it) {
                        is GetMoviesUiState.LoadingState -> "Loading"
                        is GetMoviesUiState.ErrorState -> "An Error Occurred: ${it.message}"
                        is GetMoviesUiState.SuccessState -> "On Success: ${it.data}"
                    }

                    Log.i("MainActivity", message)
                }
            }
        }
    }

}