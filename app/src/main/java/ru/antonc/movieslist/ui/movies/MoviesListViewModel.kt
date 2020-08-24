package ru.antonc.movieslist.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.antonc.movieslist.common.extensions.combineWith
import ru.antonc.movieslist.repository.MoviesRepository

class MoviesListViewModel(private val repository: MoviesRepository) : ViewModel() {

    val isLoad = repository.isLoad

    val errorMessage = repository.errorMessage

    private val _isFilterByYearOn = MutableLiveData(false)
    val isFilterByYearOn: LiveData<Boolean> = _isFilterByYearOn

    val moviesList =
        repository.moviesList.combineWith(isFilterByYearOn) { moviesList, isFilterByYearOn ->
            return@combineWith (if (isFilterByYearOn == true)
                moviesList?.filter { movie -> movie.year == 2020 }
            else moviesList) ?: emptyList()
        }

    fun updateMovies() {
        viewModelScope.launch {
            repository.updateMoviesList()
        }
    }

    fun changeFilterSwitchValue() {
        _isFilterByYearOn.value?.let { currentValue ->
            _isFilterByYearOn.value = !currentValue
        }
    }
}