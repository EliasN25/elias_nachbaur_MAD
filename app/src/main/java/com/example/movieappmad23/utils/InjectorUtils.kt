package com.example.movieappmad23.utils

import android.content.Context
import com.example.movieappmad23.data.MovieDatabase
import com.example.movieappmad23.repositories.MovieRepository
import com.example.movieappmad23.viewmodels.factory.AddMovieViewModelFactory
import com.example.movieappmad23.viewmodels.factory.FavoriteViewModelFactory
import com.example.movieappmad23.viewmodels.factory.MoviesViewModelFactory


object InjectorUtils {
    private fun getMovieRepository(context: Context): MovieRepository{
        return MovieRepository(MovieDatabase.getDatabse(context).taskDao())
    }

    fun provideMovieViewModelFactory(context: Context): MoviesViewModelFactory {
        val repository = getMovieRepository(context)
        return MoviesViewModelFactory(repository)
    }


    fun provideFavoriteViewModelFactory(context: Context): FavoriteViewModelFactory {
        val repository = getMovieRepository(context)
        return FavoriteViewModelFactory(repository = repository)
    }


    fun provideAddMovieScreenViewModelFactory(context: Context): AddMovieViewModelFactory {
        val repository = getMovieRepository(context)
        return AddMovieViewModelFactory(repository = repository)
    }
}