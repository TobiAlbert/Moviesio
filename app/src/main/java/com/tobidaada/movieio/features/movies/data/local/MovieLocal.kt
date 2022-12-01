package com.tobidaada.movieio.features.movies.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tobidaada.movieio.features.movies.domain.models.Movie

@Entity(tableName = "movies")
data class MovieLocal(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo( name ="release_date")
    val releaseDate: String,

    @ColumnInfo(name = "poster_path")
    val posterPath: String,

    @ColumnInfo(name = "rating")
    val rating: Float,

    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String?
) {
    companion object {
        fun fromDomain(domainModel: Movie): MovieLocal = with(domainModel) {
            MovieLocal(
                id = id,
                title = title,
                overview = overview,
                releaseDate = releaseDate,
                posterPath = posterPath,
                rating = rating,
                backdropPath = backdropPath,
            )
        }
    }

    fun toDomain(): Movie =
        Movie(
            id = id,
            title = title,
            overview = overview,
            releaseDate = releaseDate,
            posterPath = posterPath,
            rating = rating,
            backdropPath = backdropPath,
        )
}