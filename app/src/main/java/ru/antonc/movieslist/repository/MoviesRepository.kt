package ru.antonc.movieslist.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import ru.antonc.movieslist.R
import ru.antonc.movieslist.common.EventContent
import ru.antonc.movieslist.data.dao.MovieDAO
import ru.antonc.movieslist.rest.Api
import java.io.IOException
import java.net.UnknownHostException

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

    private val _isLoad = MutableLiveData(false)
    val isLoad: LiveData<Boolean> = _isLoad

    private val _errorMessage = MutableLiveData<EventContent<Int>>()
    val errorMessage: LiveData<EventContent<Int>> = _errorMessage

    val moviesList = movieDAO.getMoviesList()

    init {
        runBlocking(Dispatchers.IO) {
            if (movieDAO.getCountMovies() == 0) {
                updateMoviesList(true)
            }
        }
    }

    suspend fun updateMoviesList(isQuiet: Boolean = false) {
        if (_isLoad.value == true)
            return

        withContext(Dispatchers.IO) {
            if (!isQuiet)
                _isLoad.postValue(true)

            try {
                val response = api.getMoviesList().execute()

                if (response.isSuccessful) {
                    response.body()?.let { moviesList ->
                        movieDAO.updateData(moviesList)
                    }
                }
            } catch (error: IOException) {
                if (!isQuiet)
                    _errorMessage.postValue(
                        when (error) {
                            is UnknownHostException -> EventContent(R.string.error_no_connection)
                            else -> EventContent(R.string.error_while_update)
                        }
                    )
            } finally {
                _isLoad.postValue(false)
            }
        }
    }
}