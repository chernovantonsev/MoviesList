package ru.antonc.movieslist.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.antonc.movieslist.repository.MoviesRepository
import ru.antonc.movieslist.ui.movies.MoviesListViewModel

class MoviesListViewModelFactory(
    private val repository: MoviesRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MoviesListViewModel(repository) as T
    }
}