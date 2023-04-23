package com.example.movieappmad23.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieappmad23.models.Movie
import androidx.room.TypeConverters
import com.example.movieappmad23.utils.CustomConverters

@Database(
    entities = [Movie::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(CustomConverters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun taskDao(): MovieDao

    companion object{
        @Volatile
        private var Instance: MovieDatabase? = null

        fun getDatabse(context: Context): MovieDatabase {
            return Instance?: synchronized(this){
                Room.databaseBuilder(context, MovieDatabase::class.java, "movie_db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        Instance = it
                    }
            }
        }
    }
}
