package ru.antonc.movieslist.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.antonc.movieslist.data.entities.Movie


@Dao
abstract class MovieDAO : BaseDAO<Movie> {

    @Query("SELECT * from ${Movie.TABLE_NAME}")
    abstract fun getMoviesList(): LiveData<List<Movie>>

    @Query("SELECT COUNT(*) from ${Movie.TABLE_NAME}")
    abstract fun getCountMovies(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(listMovies: List<Movie>)

    @Query("DELETE FROM ${Movie.TABLE_NAME}")
    abstract fun clearTable()

    @Transaction
    open fun updateData(moviesList: List<Movie>) {
        clearTable()
        insertAll(moviesList)
    }
}