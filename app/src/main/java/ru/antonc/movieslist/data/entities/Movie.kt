package ru.antonc.movieslist.data.entities

import androidx.room.Entity

@Entity(tableName = Movie.TABLE_NAME, primaryKeys = ["id"])
data class Movie(
    val id: String,
    val poster: String,
    val year: Int
)
{
    companion object {
        const val TABLE_NAME = "movie_table"
    }
}