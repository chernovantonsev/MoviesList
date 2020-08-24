package ru.antonc.movieslist.rest

import retrofit2.Call
import retrofit2.http.GET
import ru.antonc.movieslist.data.entities.Movie

interface Api {

    @GET("movies.json")
    fun getMoviesList(): Call<List<Movie>>
}