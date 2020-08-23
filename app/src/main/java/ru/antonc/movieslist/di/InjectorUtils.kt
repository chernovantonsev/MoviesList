package ru.antonc.movieslist.di

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.antonc.movieslist.BuildConfig
import ru.antonc.movieslist.common.BASE_URL
import ru.antonc.movieslist.data.AppDatabase
import ru.antonc.movieslist.repository.MoviesRepository
import ru.antonc.movieslist.rest.Api

object InjectorUtils {

    private fun provideMoviesRepository(context: Context): MoviesRepository {
        return MoviesRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).movieDAO(),
            provideRestartService(provideHttpClient())
        )
    }

    fun provideMoviesListViewModelFactory(context: Context): MoviesListViewModelFactory {
        return MoviesListViewModelFactory(provideMoviesRepository(context))
    }

    fun provideHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }

        return httpClient.build()
    }

    fun provideRestartService(httpClient: OkHttpClient): Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create(Api::class.java)
    }
}