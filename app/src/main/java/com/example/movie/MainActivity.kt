package com.example.movie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movie.models.Movie
import com.example.movie.models.getMovies
import com.example.movie.ui.theme.MovieTheme
import kotlin.random.Random
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    TopAppBar()
                }
            }
        }
    }
}


var bgColor = Color(red = 0, green = 0, blue = 0, alpha = 0)
var favs: ArrayList<Movie> = ArrayList()


@Composable
fun MovieRow(movie: Movie) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(all = 10.dp)
            .clip(shape = RoundedCornerShape(size = 10.dp))
    ) {
        Box {
            Row {
                val randomImg by rememberSaveable {
                    mutableStateOf(Random.nextInt(from = 0, until = movie.images.size - 1))
                }
                AsyncImage(
                    model = movie.images[randomImg],
                    contentDescription = movie.title
                )
            }
            Box {
                var heartClicked by rememberSaveable {
                    mutableStateOf(false)
                }
                IconButton(
                    modifier = Modifier.offset(x = 340.dp),
                    enabled = true,
                    onClick = ({
                        if (!heartClicked) {
                            addToFavouriteMovies(movie)
                        } else {
                            removeFromFavouriteMovies(movie)
                        }
                        heartClicked = !heartClicked
                    })
                ) {
                    Icon(
                        imageVector = if (!heartClicked) {
                            Icons.Default.FavoriteBorder
                        } else {
                            Icons.Default.Favorite
                        },
                        contentDescription = "Favorite",
                        tint = Color.Cyan
                    )
                }
            }
        }
        var movExtend by rememberSaveable {
            mutableStateOf(false)
        }
        Row(
            modifier = Modifier
                .background(color = Color.LightGray)
                .fillMaxWidth(fraction = 1f)
                .padding(start = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = movie.title,
                fontSize = 30.sp
            )
            IconButton(
                enabled = true,
                onClick = ({
                    movExtend = !movExtend
                })
            ) {
                Icon(
                    imageVector = if (!movExtend) {
                        Icons.Default.KeyboardArrowUp
                    } else {
                        Icons.Default.KeyboardArrowDown
                    },
                    contentDescription = if (!movExtend) {
                        "KeyboardArrowUp"
                    } else {
                        "KeyboardArrowDown"
                    },
                    tint = Color.DarkGray
                )
            }
        }
        AnimatedVisibility(visible = movExtend) {
            Row(
                modifier = Modifier
                    .background(color = Color.LightGray)
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = "Actors: ${movie.actors}\nGenre: ${movie.genre}\n" +
                            "Year: ${movie.year}\nDirector: ${movie.director}\n" +
                            "Rating: ${movie.rating}\n\nPlot: ${movie.plot}",
                    fontSize = 22.sp
                )
            }
        }
    }
}

@Composable
fun TopAppBar() {
    Scaffold(
        topBar = {
            var menuButtonClicked by rememberSaveable {
                mutableStateOf(false)
            }
            var showFavouriteMovies by rememberSaveable {
                mutableStateOf(false)
            }
            Box(modifier = Modifier.background(bgColor)) {
                TopAppBar(
                    title = {
                        Text(
                            text = "MovieApp",
                            color = Color.White
                        )
                    },
                    backgroundColor = Color.Black,
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                bottomStart = 10.dp,
                                bottomEnd = 10.dp
                            )
                        ),
                    navigationIcon = {
                        IconButton(onClick = { menuButtonClicked = !menuButtonClicked }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "MenuButton",
                                tint = Color.White
                            )
                            DropdownMenu(
                                expanded = menuButtonClicked,
                                onDismissRequest = { menuButtonClicked = false }
                            ) {
                                DropdownMenuItem(onClick = { exitProcess(0) }) {
                                    Text(text = "Quit")
                                    Icon(
                                        imageVector = Icons.Default.ExitToApp,
                                        "Quit"
                                    )
                                }
                            }
                        }
                    }, actions = {
                        IconButton(onClick = { showFavouriteMovies = !showFavouriteMovies }) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "FavouriteMovies",
                                tint = Color.White
                            )
                            DropdownMenu(
                                expanded = showFavouriteMovies,
                                onDismissRequest = { showFavouriteMovies = false }
                            ) {
                                if (favs.size == 0) {
                                    DropdownMenuItem(
                                        enabled = false,
                                        onClick = { /* STUB */ }
                                    ) {
                                        Text(text = "No Favourites!")
                                    }
                                }
                                favs.forEach(action = { movie ->
                                    DropdownMenuItem(onClick = { /* STUB */ }) {
                                        Text(text = movie.title)
                                    }
                                })
                            }
                        }
                    }
                )
            }
        }, content = { paddingValues ->
            Box(modifier = Modifier.background(bgColor)) {
                LazyColumn(
                    contentPadding = paddingValues,
                    content = {
                        items(getMovies()) { movie ->
                            MovieRow(movie)
                        }
                    }
                )
            }
        }
    )
}

fun addToFavouriteMovies(movie: Movie) {
    favs.add(movie)
}
fun removeFromFavouriteMovies(movie: Movie) {
    favs.remove(movie)
}

