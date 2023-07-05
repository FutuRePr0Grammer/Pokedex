package com.example.pokedex

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

import javax.inject.Inject

class MainActivity : ComponentActivity() {

    /*Will need:
    * name
    * type
    * sprite (front_default)*/


    private val viewModel: PokemonViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            var grassColor = Color.Green
            var fireColor = Color.Red
            var waterColor = Color.Blue
            var electricColor = Color.Yellow

            //var increment = 0
            //var pokemonNameList: ArrayList<String> = arrayListOf()

            var types: List<String> = listOf("grass")
            //types = TODO

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
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                viewModel.viewState.pokemonNames.forEach { pokemonName ->
                    item {
                        pokemonCardComposable(
                            name = pokemonName,
                            //types = viewModel.viewState.pokemonTypes,
                            types = types,
                            imageUrl = "TEST",
                            //imageUrl = viewModel.viewState.pokemonImageUrls[increment],
                            color = chosenColor
                        )
                    }
                    //increment++
                    //pokemonNameList.add(pokemonName)
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
                    Text(types[0])
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
