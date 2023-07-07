package com.example.pokedex

import android.graphics.fonts.FontStyle
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import java.time.format.TextStyle

class MainActivity : ComponentActivity() {

    /*Will need:
    * name
    * type
    * sprite (front_default)*/


    private val viewModel: PokemonViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                viewModel.pokemon.forEach { pokemon ->
                    item {
                        pokemonCardComposable(
                            name = pokemon.name,
                            types = pokemon.type,
                            imageUrl = pokemon.image,
                            color = pokemon.selectedColor
                        )
                    }

                }
            }

        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
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
                    Text(
                        text = name,
                        style = androidx.compose.ui.text.TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                    types.forEach { type ->
                        Text(
                            text = type,//types.toString(),
                            //Modifier.border(BorderStroke(2.dp, Color.LightGray)),
                            style = androidx.compose.ui.text.TextStyle(
                                //ff means color, 100% opacity. This is white, 80% opacity (0x80)
                                background = Color(0x80ffffff),
                                fontSize = 17.sp
                            )
                        )
                    }
                }
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .decoderFactory(SvgDecoder.Factory())
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(160.dp)
                )
            }
        }
    }

    @Composable
    fun individualPokemonCard(name: String, types: List<String>, imageUrl: String, color: Color){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(color)
                .size(150.dp)
        ) {

            Text(name)
            Text(types.toString())
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                contentDescription = null
            )
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    @Preview
    @Composable
    fun pokemonCardComposablePreview() {
        pokemonCardComposable("Bulbasaur", listOf("grass", "fire"), "https://oyster.ignimgs.com/mediawiki/apis.ign.com/pokemon-x-y-version/5/51/Bulbasaur.jpg?width=325", Color(0xff67f041))
    }

    @Preview
    @Composable
    fun individualPokemonCardPreview() {
        individualPokemonCard("Bulbasaur", listOf("grass", "water"), "https://oyster.ignimgs.com/mediawiki/apis.ign.com/pokemon-x-y-version/5/51/Bulbasaur.jpg?width=325", Color(0xfff05241))
    }
}
