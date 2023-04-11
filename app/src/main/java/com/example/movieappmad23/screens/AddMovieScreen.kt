package com.example.movieappmad23.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieappmad23.R
import com.example.movieappmad23.models.Genre
import com.example.movieappmad23.models.ListItemSelectable
import com.example.movieappmad23.models.MovieViewModel
import com.example.movieappmad23.widgets.SimpleTopAppBar

@Composable
fun AddMovieScreen(navController: NavController, viewModel: MovieViewModel){
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            SimpleTopAppBar(arrowBackClicked = { navController.popBackStack() }) {
                Text(text = stringResource(id = R.string.add_movie))
            }
        },
    ) { padding ->
        MainContent(Modifier.padding(padding), viewModel = viewModel)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainContent(modifier: Modifier = Modifier, viewModel: MovieViewModel) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(10.dp)
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {

            //Some things were simplified here and outsourced to the MovieViewModel (mutableStates, addMovie function, etc...)

            TextInputField(
                label = R.string.enter_movie_title,
                text = viewModel.title,
                validateMethod = {viewModel.validate("title")},
                errorState = viewModel.errorTitle
            )

            TextInputField(
                label = R.string.enter_movie_year,
                text = viewModel.year,
                validateMethod = {viewModel.validate("year")},
                errorState = viewModel.errorYear
            )

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(R.string.select_genres),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.h6)

            LazyHorizontalGrid(
                modifier = Modifier.height(100.dp),
                rows = GridCells.Fixed(3)){
                items(viewModel.genreItems.value) { genreItem ->
                    Chip(
                        modifier = Modifier.padding(2.dp),
                        colors = ChipDefaults.chipColors(
                            backgroundColor = if (genreItem.isSelected)
                                colorResource(id = R.color.purple_200)
                            else
                                colorResource(id = R.color.white)
                        ),
                        onClick = {
                            viewModel.genreItems.value = viewModel.genreItems.value.map {
                                if (it.title == genreItem.title) {
                                    genreItem.copy(isSelected = !genreItem.isSelected)
                                } else {
                                    it
                                }
                            }
                            viewModel.validate("genres")
                        }
                    ) {
                        Text(text = genreItem.title)
                    }
                }
            }

            TextInputField(
                label = R.string.enter_director,
                text = viewModel.director,
                validateMethod = {viewModel.validate("director")},
                errorState = viewModel.errorDirector
            )

            TextInputField(
                label = R.string.enter_actors,
                text = viewModel.actors,
                validateMethod = {viewModel.validate("actors")},
                errorState = viewModel.errorActors
            )

            TextInputField(
                label = R.string.enter_plot,
                text = viewModel.plot,
                validateMethod = {viewModel.validate("plot")},
                errorState = viewModel.errorPlot
            )

            TextInputField(
                R.string.enter_rating,
                viewModel.rating,
                validateMethod = {viewModel.validate("rating")},
                viewModel.errorRating


            )

            Button(
                enabled = viewModel.isDisabled.value,
                onClick = {
                    val genreList: MutableList<Genre> = mutableListOf()
                    viewModel.genreItems.value.filter { it.isSelected }.forEach { genreList.add(Genre.valueOf(it.title)) }

                    viewModel.addMovie(
                        viewModel.title.value,
                        viewModel.year.value,
                        viewModel.director.value,
                        genreList,
                        viewModel.actors.value,
                        viewModel.plot.value,
                        viewModel.rating.value
                    )
                }) {
                Text(text = stringResource(R.string.add))
            }
        }
    }
}

@Composable
private fun TextInputField(
    label: Int,
    text: MutableState<String>,
    validateMethod: () -> Unit,
    errorState: MutableState<Boolean>
) {
    OutlinedTextField(
        value = text.value,
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = {
            text.value = it
            validateMethod()
        },
        label = { Text(stringResource(id = label)) },
        isError = errorState.value
    )
}
