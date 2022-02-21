package com.tobidaada.movieio.features.movies.presentation.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tobidaada.movieio.databinding.ListItemTvShowBinding
import com.tobidaada.movieio.features.movies.domain.entities.Movie
import com.tobidaada.movieio.utils.POSTER_IMAGE_BASE_URL

class MovieViewHolder(
    private val binding: ListItemTvShowBinding,
    private val onItemSelected: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) = with(binding) {
        movieTitle.text = movie.title
        rating.text = movie.rating.toString()
        releaseDate.text = movie.releaseDate.split("-").first()
        overview.text = movie.overview

        poster.load("$POSTER_IMAGE_BASE_URL${movie.posterPath}") {
            crossfade(true)
        }

        root.setOnClickListener { onItemSelected(movie.id) }
    }
}