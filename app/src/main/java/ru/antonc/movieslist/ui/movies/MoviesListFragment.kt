package ru.antonc.movieslist.ui.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ru.antonc.movieslist.R
import ru.antonc.movieslist.di.InjectorUtils

class MoviesListFragment : Fragment() {

    private val viewModel: MoviesListViewModel by viewModels {
        InjectorUtils.provideMoviesListViewModelFactory(requireContext())
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.movies_list_fragment, container, false)
    }

}