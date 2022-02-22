package com.tobidaada.movieio.features.movies.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.tobidaada.movieio.databinding.FragmentMovieBinding
import com.tobidaada.movieio.features.movies.domain.entities.Movie
import com.tobidaada.movieio.features.movies.presentation.MovieViewModel
import com.tobidaada.movieio.utils.BACK_DROP_IMAGE_BASE_URL
import dagger.hilt.android.AndroidEntryPoint

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

        movieViewModel.getMovie(args.movieId).observe(viewLifecycleOwner) { movie: Movie? ->
            movie ?: return@observe
            // update ui with movie info
            binding.movieTitle.text = movie.title
            binding.movieSummary.text = movie.overview
            binding.movieYear.text = movie.releaseDate.split("-").first()

            movie.backdropPath?.let { binding.moviePoster.load("$BACK_DROP_IMAGE_BASE_URL$it") }
        }
    }
}