package ru.antonc.movieslist.repository

import ru.antonc.movieslist.data.dao.MovieDAO
import ru.antonc.movieslist.rest.Api

class MoviesRepository(
    private val movieDAO: MovieDAO,
    private val api: Api
) {


    companion object {
        @Volatile
        private var instance: MoviesRepository? = null

        fun getInstance(movieDAO: MovieDAO, api: Api) =
            instance ?: synchronized(this) {
                instance ?: MoviesRepository(movieDAO, api).also { instance = it }
            }
    }
}