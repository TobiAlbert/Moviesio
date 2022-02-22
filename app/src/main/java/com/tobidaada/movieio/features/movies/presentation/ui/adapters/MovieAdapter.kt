package com.tobidaada.movieio.features.movies.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.tobidaada.movieio.databinding.ListItemTvShowBinding
import com.tobidaada.movieio.features.movies.domain.entities.Movie

class MovieAdapter (private val onMovieItemSelected: (Int) -> Unit): ListAdapter<Movie, MovieViewHolder>(DiffCallback) {

    private lateinit var binding: ListItemTvShowBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ListItemTvShowBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding, onMovieItemSelected)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        getItem(position)?.let { holder.bind(it) } ?: Unit

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == oldItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }
}