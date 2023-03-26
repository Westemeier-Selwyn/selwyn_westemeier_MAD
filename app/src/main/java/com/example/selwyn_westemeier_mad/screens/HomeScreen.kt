package com.example.selwyn_westemeier_mad.screens

import android.icu.text.CaseMap.Title
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.selwyn_westemeier_mad.R
import com.example.selwyn_westemeier_mad.models.Movie
import com.example.selwyn_westemeier_mad.models.getMovies

@Composable
fun HomeScreen(navController: NavController) {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            TopAppBar()
            MyList(navController)
        }
        //MyList()
        //Greeting()
        //WelcomeText(modifier = Modifier.padding(16.dp), text = "welcome to my app!")
    }
}

@Composable
fun TopAppBar() {
    var expanded by remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text(text = "Movies") },
        actions = {
            IconButton(onClick = { expanded = true }) {

                Icon(Icons.Filled.MoreVert, contentDescription = "Kotlett")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false}
            ) {
                DropdownMenuItem(onClick = {}, modifier = Modifier.width(130.dp)) {
                    Icon(Icons.Default.Favorite, contentDescription = "Favorites" )
                    Text(" Favorites")

                }
            }
        }
    )
}


@Preview
@Composable
fun MyList(navController: NavController = rememberNavController(),
           movies: List<Movie> = getMovies()){
    LazyColumn{
        items(movies) {movie ->
            MovieRow(
                movie = movie,
            )  { movieId ->
                Log.d("MyList", "item clicked $movieId")
                // navigate to detailscreen
                navController.navigate("detail/$movieId")
            }
        }
    }
}


@Composable
fun MovieRow(movie: Movie, onItemClick: (String) -> Unit = {}) {
    var expandedState by remember { mutableStateOf(false)}
    val rotatedState by animateFloatAsState(targetValue = if(expandedState) 180f else 0f)

    Card(modifier = Modifier
        .animateContentSize(
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearOutSlowInEasing
            )
        )

        .fillMaxWidth()
        .padding(5.dp)
        .clickable { onItemClick(movie.id) },
        shape = RoundedCornerShape(corner = CornerSize(15.dp)),
        elevation = 5.dp
    ) {
        Column {
            Box(modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
            ) {
                val painter = rememberImagePainter(data = movie.images[0],
                    builder = {
                    })
                Image(
                    painter = painter,
                    contentDescription = "Movie Poster",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                    contentAlignment = Alignment.TopEnd
                ){
                    Icon(
                        tint = MaterialTheme.colors.secondary,
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Add to favorites")
                }
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(movie.title, style = MaterialTheme.typography.h6)
                IconButton(
                    modifier = Modifier.rotate(rotatedState),
                    onClick = {expandedState = !expandedState})
                {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Show details")
                }

            }
            if(expandedState){
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)

                ) {
                    Text(text = "Director: ${movie.director}\n" +
                                "Released: ${movie.year}\n" +
                                "Genre: ${movie.genre}\n" +
                                "Actors: ${movie.actors}\n" +
                                "Rating: ${movie.rating}"
                    )

                }
                Divider(color = Color.LightGray, thickness = 1.dp)
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                ) {
                   Text(text = "Plot: ${movie.plot}")
                }
            }


        }
    }
}