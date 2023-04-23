package com.example.movieappmad23.utils

import androidx.room.TypeConverter
import com.example.movieappmad23.models.Genre

class CustomConverters {

    @TypeConverter
    fun listToString(value: List<String>) = value.joinToString(",")

    @TypeConverter
    fun stringOfString(value: String) = value.split(",").map { it.trim() }

    @TypeConverter
    fun listGenreToString(value: List<Genre>): String =
        value.joinToString(separator = ",") {
            it.toString()
        }

    @TypeConverter
    fun stringToListGenre(value: String): List<Genre> = value.split(",").map {
        Genre.valueOf(it)
    }
}