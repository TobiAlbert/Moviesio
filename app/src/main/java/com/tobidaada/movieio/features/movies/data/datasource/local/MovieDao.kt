package com.tobidaada.movieio.features.movies.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAllPopularMovies(): Flow<List<MovieLocal>>

    @Query("SELECT * FROM movies WHERE id=:id LIMIT 1")
    suspend fun getMovie(id: Int): MovieLocal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: List<MovieLocal>)

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()
}