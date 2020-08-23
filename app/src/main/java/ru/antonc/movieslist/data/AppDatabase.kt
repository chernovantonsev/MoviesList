package ru.antonc.movieslist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.antonc.movieslist.data.dao.MovieDAO
import ru.antonc.movieslist.data.entities.Movie

@Database(
    version = 1,
    entities = [Movie::class],
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDAO(): MovieDAO

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context, AppDatabase::class.java,
                context.packageName
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}