package com.example.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest

class MainActivity : ComponentActivity() {

    /*Will need:
    * name
    * type
    * sprite (front_default)*/


    private val viewModel: PokemonViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var selectedColor: Color


        setContent {
            /*var grassColor = Color.Green
            var fireColor = Color.Red
            var waterColor = Color.Blue
            var electricColor = Color.Yellow


            var types: List<String> = listOf("grass")

            var chosenColor: Color

            if(types[0] == "grass"){
                chosenColor = grassColor
            }
            else if(types[0] == "fire"){
                chosenColor = fireColor
            }
            else if(types[0] == "water"){
                chosenColor = waterColor
            }
            else{
                chosenColor = electricColor
            }*/


            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                viewModel.pokemon.forEach { pokemon ->
                    if(pokemon.type[0] == "grass"){
                        selectedColor = viewModel.grassColor
                    }
                    else if(pokemon.type[0] == "fire"){
                        selectedColor = viewModel.fireColor
                    }
                    else if(pokemon.type[0] == "poison"){
                        selectedColor = viewModel.poisonColor
                    }
                    else if(pokemon.type[0] == "water"){
                        selectedColor = viewModel.waterColor
                    }
                    else if(pokemon.type[0] == "electric"){
                        selectedColor = viewModel.electricColor
                    }
                    else if(pokemon.type[0] == "bug"){
                        selectedColor = viewModel.bugColor
                    }
                    else{
                        selectedColor = viewModel.normalColor
                    }
                    item {
                        pokemonCardComposable(
                            name = pokemon.name,
                            types = pokemon.type,
                            imageUrl = pokemon.image,
                            color = selectedColor
                        )
                    }

                }
            }

        }
    }
    @Composable
    fun pokemonCardComposable(name: String, types: List<String>, imageUrl: String, color: Color ) {

        Box(
            modifier = Modifier
                .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 8.dp
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(color)
                    .size(150.dp)
            ) {

                Column {
                    Text(name)
                    Text(types.toString())
                }
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .decoderFactory(SvgDecoder.Factory())
                        .build(),
                    contentDescription = null
                )
            }
        }
    }



    @Preview
    @Composable
    fun pokemonCardComposablePreview() {
        pokemonCardComposable("Bulbasaur", listOf("grass"), "https://oyster.ignimgs.com/mediawiki/apis.ign.com/pokemon-x-y-version/5/51/Bulbasaur.jpg?width=325", Color.Red)
    }
}
