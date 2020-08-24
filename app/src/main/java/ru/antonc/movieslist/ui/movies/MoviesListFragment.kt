package ru.antonc.movieslist.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.antonc.movieslist.R
import ru.antonc.movieslist.di.InjectorUtils

class MoviesListFragment : Fragment() {

    private val viewModel: MoviesListViewModel by viewModels {
        InjectorUtils.provideMoviesListViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.movies_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val swipeRefreshLayout =
            view.findViewById<SwipeRefreshLayout>(R.id.swipe_to_update_movies)?.apply {
                setOnRefreshListener {
                    viewModel.updateMovies()
                }
            }

        viewModel.isLoad.observe(viewLifecycleOwner) { isLoad ->
            swipeRefreshLayout?.isRefreshing = isLoad
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { messageEvent ->
            messageEvent.getContentIfNotHandled()?.let { messageResource ->
                Toast.makeText(requireContext(), getText(messageResource), Toast.LENGTH_SHORT)
                    .show()
            }
        }

        val moviesAdapter = MoviesListAdapter()

        view.findViewById<RecyclerView>(R.id.rv_list_movies)?.apply {
            adapter = moviesAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }

        val titlePlaceholder = view.findViewById<TextView>(R.id.title_placeholder_no_items)
        val messagePlaceholder = view.findViewById<TextView>(R.id.text_swipe_to_refresh)

        viewModel.moviesList.observe(viewLifecycleOwner) { moviesList ->
            moviesList.isNotEmpty().let { listIsNotEmpty ->
                titlePlaceholder.visibility = if (listIsNotEmpty) View.GONE else View.VISIBLE
                messagePlaceholder.visibility = if (listIsNotEmpty) View.GONE else View.VISIBLE
            }

            moviesAdapter.submitList(moviesList)
        }

        val switchFilter = view.findViewById<SwitchCompat>(R.id.switch_filter).apply {
            setOnClickListener {
                viewModel.changeFilterSwitchValue()
            }
        }

        view.findViewById<TextView>(R.id.tv_filter_by_year).apply {
            setOnClickListener {
                viewModel.changeFilterSwitchValue()
            }
        }

        viewModel.isFilterByYearOn.observe(viewLifecycleOwner) { isFilterByYearOn ->
            switchFilter.isChecked = isFilterByYearOn
        }
    }
}