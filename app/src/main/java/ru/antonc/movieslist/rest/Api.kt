package ru.antonc.movieslist.rest

import retrofit2.http.GET
import ru.antonc.movieslist.data.entities.Movie

interface Api {

    @GET("movies.json")
    fun getMoviesList(): List<Movie>
}