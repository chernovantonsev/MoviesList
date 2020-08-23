package ru.antonc.movieslist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.antonc.movieslist.ui.movies.MoviesListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MoviesListFragment())
                    .commitNow()
        }
    }
}