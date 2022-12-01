package com.tobidaada.movieio.features.movies.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import coil.load
import com.tobidaada.movieio.databinding.FragmentMovieBinding
import com.tobidaada.movieio.utils.BACK_DROP_IMAGE_BASE_URL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private val movieViewModel: MovieViewModel by viewModels()
    private val args: MovieFragmentArgs by navArgs()
    private lateinit var binding: FragmentMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        movieViewModel.onDisplayMovie(args.movieId)
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                movieViewModel.viewState.collect(::handleUiState)
            }
        }
    }

    private fun handleUiState(state: MovieViewModel.ViewState) {
        val movie = state.movie

        if (movie == null) {
            // handle state when movie does not exist
            handleNoMovieState()
            return
        }

        binding.movieTitle.text = movie.title
        binding.movieSummary.text = movie.overview
        binding.movieYear.text = movie.releaseDate.split("-").first()

        movie.backdropPath?.let { binding.moviePoster.load("$BACK_DROP_IMAGE_BASE_URL$it") }
    }

    private fun handleNoMovieState() = Unit
}