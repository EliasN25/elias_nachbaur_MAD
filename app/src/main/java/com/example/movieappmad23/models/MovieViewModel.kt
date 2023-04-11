package com.example.movieappmad23.models

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.movieappmad23.widgets.FavoriteIcon

class MovieViewModel : ViewModel() {

    var movies = getMovies().toMutableStateList()
    val allMovies: List<Movie>
        get() = movies

    //MutableList for the favorit movies
    var moviesfavourite = mutableListOf<Movie>().toMutableStateList()
    val favoriteMovies: List<Movie>
        get() = moviesfavourite

    var addMovie: Movie = Movie("", "", "", listOf(), "", "", "", listOf(), 0.0f)

    var isDisabled: MutableState<Boolean> = mutableStateOf(false)

    //Mutable states for title, year, director, etc.

    var title = mutableStateOf(addMovie.title)
    var errorTitle: MutableState<Boolean> = mutableStateOf(false)

    val year = mutableStateOf(addMovie.year)
    var errorYear: MutableState<Boolean> = mutableStateOf(false)

    var director = mutableStateOf(addMovie.director)
    var errorDirector: MutableState<Boolean> = mutableStateOf(false)

    var actors = mutableStateOf(addMovie.actors)
    var errorActors: MutableState<Boolean> = mutableStateOf(false)

    var plot = mutableStateOf(addMovie.plot)
    var errorPlot: MutableState<Boolean> = mutableStateOf(false)

    var rating = mutableStateOf(addMovie.rating.toString().replace("0.0", ""))
    var errorRating: MutableState<Boolean> = mutableStateOf(false)

    var genreItems = mutableStateOf(
        Genre.values().map { genre ->
            ListItemSelectable(
                title = genre.toString(),
                isSelected = false
            )
        }
    )
    var genreError: MutableState<Boolean> = mutableStateOf(false)

    //Initialize the input
    fun init() {
        validate("title")
        validate("year")
        validate("director")
        validate("actors")
        validate("plot")
        validate("rating")
        validate("genres")

    }

    //Add the Movie to the existing ones
    fun addMovie(
        title: String,
        year: String,
        director: String,
        genres: List<Genre>,
        actors: String,
        plot: String,
        rating: String
    ) {
        val newMovie = Movie(
            id = "$title + $year + $director+ $genres + $actors",
            title = title,
            year = year,
            director = director,
            genre = genres,
            actors = actors,
            plot = plot,
            rating = rating.toFloat(), images = listOf(
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTQ4NzM2Mjc5MV5BMl5BanBnXkFtZTcwMTkwOTY3Nw@@._V1_SX1777_CR0,0,1777,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTg4Njk4MzY0Nl5BMl5BanBnXkFtZTgwMzIyODgxMzE@._V1_SX1500_CR0,0,1500,999_AL_.jpg",
                )
        )
        movies.add(newMovie)

    }

    //Toggle if Favorite or not
    fun toggleFavorite(movie: Movie) {
        movies.find { it.id == movie.id }?.let { task ->
            task.isFavorite = !task.isFavorite
            if (task.isFavorite) {
                moviesfavourite.add(movie)
            } else {
                moviesfavourite.remove(movie)
            }
        }
    }

    //Validate the input
    fun validate(field: String) {
        when (field) {
            "title" -> errorTitle.value = title.value.isEmpty()
            "year" -> errorYear.value = year.value.isEmpty()
            "director" -> errorDirector.value = director.value.isEmpty()
            "actors" -> errorActors.value = actors.value.isEmpty()
            "plot" -> errorPlot.value = plot.value.isEmpty()
            "rating" -> {
                try {
                    rating.value.toFloat()
                    errorRating.value = false
                } catch (e: java.lang.Exception) {
                    errorRating.value = true
                } finally {
                    Enable()
                }
            }
            "genres" -> {
                genreError.value = true
                genreItems.value.forEach genres@{
                    if (it.isSelected) {
                        genreError.value = false
                        return@genres
                    }
                }
                Enable()
            }
        }
        Enable()
    }

    //Enable
    private fun Enable() {
        isDisabled.value =
            (errorTitle.value.not()
                    && errorYear.value.not()
                    && errorDirector.value.not()
                    && errorActors.value.not()
                    && errorPlot.value.not()
                    && errorRating.value.not()
                    && genreError.value.not()
                    )
    }
}