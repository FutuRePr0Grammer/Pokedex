package com.example.pokedex

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest

class MainActivity : ComponentActivity() {

    /*Will need:
    * name
    * type
    * sprite (front_default)*/


    private val viewModel: PokemonViewModel by viewModels()

    private lateinit var imageUrlMap: Map<String, String>

    private lateinit var typesMap: Map<String, List<String>>


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NavigationView()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun NavigationView() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "listScreen") {
            composable(route = "listScreen") {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(10.dp)
                ) {
                    viewModel.pokemon.forEach { pokemon ->
                        item {
                            PokemonCard(
                                navController = navController,
                                name = pokemon.capitalizedName,
                                types = pokemon.type,
                                imageUrl = pokemon.image,
                                id = pokemon.displayId,
                                color = pokemon.selectedColor
                            )
                        }

                    }
                }
            }
            composable(
                route = "detailsScreen/{pokemonName}/{id}/{imageUrl}/{types}/{color}",
                arguments = listOf(
                    navArgument("pokemonName"){
                        type = NavType.StringType
                    },
                    navArgument("id"){
                        type = NavType.StringType
                    },
                    navArgument("imageUrl"){
                        type = NavType.StringType
                    },
                    navArgument("types"){
                        type = NavType.StringType
                    },
                    navArgument("color"){
                        type = NavType.IntType
                    }
                )
            ) {
                it.arguments?.getString("pokemonName")
                    ?.let { it1 -> Log.d("Pokemon Name in Details Screen: ", it1) }
                var name = it.arguments?.getString("pokemonName")
                var id = it.arguments?.getString("id")
                var imageUrl = it.arguments?.getString("imageUrl")
                var types = it.arguments?.getString("types")
                var color = it.arguments?.getInt("color")
                if (name != null) {
                    if (id != null) {
                        if (imageUrl != null) {
                            if (types != null) {
                                if (color != null) {
                                    PokemonDetailsCard(
                                        navController = navController,
                                        name = name,
                                        id = id,
                                        imageUrl = imageUrl,
                                        types = types,
                                        color = color
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun PokemonCard(navController: NavHostController, name: String, types: List<String>, imageUrl: String, id: String, color: Color) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(color)
                .padding(10.dp)
                .clickable(
                    onClick = (

                            {   imageUrlMap = mapOf("imageUrl" to imageUrl)
                                Log.d("ImageUrl keys: ", imageUrlMap.keys.toString())
                                typesMap = mapOf("types" to types)
                                navController.navigate("detailsScreen/" + name + "/" + id + "/" + "imageUrl" + "/" + "types" +"/" + color.toArgb()) }
                            )
                )
        ) {
            Text(
                text = id,
                color = Color.White.copy(alpha = 0.4F),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Row(modifier = Modifier.fillMaxWidth()) {

                Column(
                    modifier = Modifier.weight(1F, true)
                ) {

                    Column(modifier = Modifier.padding(top = 5.dp)) {
                        types.forEach { type ->
                            Text(
                                text = type,
                                style = androidx.compose.ui.text.TextStyle(
                                    fontSize = 10.sp,
                                    color = Color.White,
                                ),
                                modifier = Modifier
                                    .padding(bottom = 5.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.White.copy(alpha = 0.4F))
                                    .padding(8.dp, 3.dp)
                            )
                        }
                    }
                }


                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .decoderFactory(SvgDecoder.Factory())
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }

    @Composable
    fun PokemonDetailsCard(navController: NavHostController, name: String, id: String, imageUrl: String, types: String, color: Int){
        Column(
            modifier = Modifier
                .fillMaxWidth(fraction = 1f)
                .fillMaxHeight(fraction = 1f)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(color))
                .padding(top = 15.dp, start = 15.dp)
        ){
            Text(
                text = "Back",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 10.sp
                ),
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .clickable(
                        onClick = { navController.navigate("listScreen") })
            )
            Row(){
                Text(
                    text = name,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    modifier = Modifier
                        //.fillMaxWidth()
                        .weight(2f)
                )
                Text(
                    text = id,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    modifier = Modifier
                        .padding(end = 10.dp, top = 5.dp)
                )
            }
            Row(){
                var typesList = typesMap.get(types)
                if (typesList != null) {
                    typesList.forEach {type ->
                        Text(
                            text = type,
                            style = TextStyle(
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            ),
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.White.copy(alpha = 0.4F))
                                .padding(2.dp)
                        )
                    }
                }
            }
            Column(
                //horizontalAlignment = Alignment.CenterHorizontally
                verticalArrangement = Arrangement.Center
            )
            {
                imageUrlMap[imageUrl]?.let { Log.d("IMAGEURL", it) }
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrlMap[imageUrl])
                        .decoderFactory(SvgDecoder.Factory())
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp),

                    contentScale = ContentScale.FillBounds,
                    alignment = Alignment.Center
                )
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Preview
    @Composable
    fun PokemonCardComposablePreview() {
        PokemonCard(rememberNavController(), "Bulbasaur", listOf("grass", "fire"), "TEST", "#003", Color(0xff67f041))
    }

    @Preview
    @Composable
    fun PokemonDetailsCardPreview(){
        PokemonDetailsCard(rememberNavController(), "Bulbasaur", "3", "TEST", "Grass", 3)
    }
}
