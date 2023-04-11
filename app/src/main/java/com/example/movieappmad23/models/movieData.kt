package com.example.movieappmad23.models

data class movieData (
    var title: String = "",
    var year: String = "",
    var genres: List<Genre> = listOf(),
    var director: String = "",
    var actors: String = "",
    var plot: String = "",
    var rating: Float = 0.0f
)