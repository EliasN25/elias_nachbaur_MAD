package com.example.movieappmad23.data

import androidx.room.*
import com.example.movieappmad23.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    // CRUD
    @Insert
    suspend fun add(movie: Movie)
    @Update
    suspend fun update(movie: Movie)
    @Delete
    suspend fun delete(movie: Movie)

    @Query("SELECT * FROM Movie")
    fun getAll(): Flow<List<Movie>>

    @Query("SELECT * FROM Movie WHERE id = :id")
    fun getMovieById(id: String): Flow<Movie?>

    @Query("SELECT * FROM Movie WHERE isFavorite = 1")
    fun getFavoriteMovies(): Flow<List<Movie>>

}