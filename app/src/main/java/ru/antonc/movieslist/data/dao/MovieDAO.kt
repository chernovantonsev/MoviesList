package ru.antonc.movieslist.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ru.antonc.movieslist.data.entities.Movie


@Dao
abstract class MovieDAO : BaseDAO<Movie> {

    @Query("SELECT * from ${Movie.TABLE_NAME}")
    abstract fun getMoviesList(): LiveData<List<Movie>>


    @Query("DELETE FROM ${Movie.TABLE_NAME}")
    abstract fun clearTable()
}