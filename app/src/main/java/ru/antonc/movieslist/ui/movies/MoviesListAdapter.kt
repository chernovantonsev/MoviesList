package ru.antonc.movieslist.ui.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.antonc.movieslist.GlideApp
import ru.antonc.movieslist.R
import ru.antonc.movieslist.data.entities.Movie

class MoviesListAdapter :
    ListAdapter<Movie, MoviesListAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_movie, parent, false
        )
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivMoviePoster: ImageView? =
            itemView.findViewById<View>(R.id.iv_movie_poster) as ImageView

        fun bindData(item: Movie) {
            ivMoviePoster?.let { ivMoviePoster ->
                GlideApp.with(ivMoviePoster.context)
                    .load(if (item.poster.isEmpty()) R.drawable.ic_no_image else item.poster)
                    .error(R.drawable.ic_no_image)
                    .centerCrop()
                    .into(ivMoviePoster)
            }
        }
    }

    private class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(
            oldItem: Movie,
            newItem: Movie
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Movie,
            newItem: Movie
        ): Boolean {
            return oldItem == newItem
        }
    }
}


