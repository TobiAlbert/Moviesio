package com.tobidaada.movieio.features.movies.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tobidaada.movieio.databinding.FragmentMovieListBinding
import com.tobidaada.movieio.features.movies.presentation.MovieViewModel
import com.tobidaada.movieio.features.movies.presentation.models.GetMoviesUiState
import com.tobidaada.movieio.features.movies.presentation.ui.adapters.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private val movieViewModel: MovieViewModel by viewModels()
    private lateinit var binding: FragmentMovieListBinding
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                movieViewModel.state.collect {
                    when (it) {
                        is GetMoviesUiState.LoadingState -> {

                        }
                        is GetMoviesUiState.ErrorState -> {
                            if (it.message.isNotEmpty()) {
                                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                        is GetMoviesUiState.SuccessState -> movieAdapter.submitList(it.data)
                    }
                }
            }
        }
    }

    private fun setupUi() {
        movieAdapter = MovieAdapter(::onMovieItemSelected)
        binding.movieRv.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }
    }

    private fun onMovieItemSelected(movieId: Int) {
        val action = MovieListFragmentDirections
            .actionMovieListFragmentToMovieFragment(movieId)

        findNavController().navigate(action)
    }
}