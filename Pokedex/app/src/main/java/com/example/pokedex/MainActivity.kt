package com.example.pokedex

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

    //TODO: figure out why retrofit ends in error even though JSON obtained (see logcat)

    //@Inject
    //lateinit var pokemonAPI: PokemonAPI

    //var apiResult: Pokemon? = null
    //var apiResult: ArrayList<Results>? = null
    //var apiResult: String? = null

    private lateinit var viewModel: PokemonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*var apiResult: String
        RetrofitApplication.instance.retrofitComponent.inject(this)
        pokemonAPI.getPokemon().enqueue(object : Callback<Pokemon>{
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                Log.d("SUCCESS", response.body()?.toString() ?: "")
                apiResult = response.body()?.results.toString()
                Log.d("APIRESULT: ", apiResult/*!!*/)
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                Log.d("ERROR", t.toString())
            }

        })*/

        //RetrofitApplication.instance.retrofitComponent.inject(this)
        viewModel = ViewModelProvider(this) [PokemonViewModel::class.java]
        var apiResult = viewModel.getPokemon()//.toString()
        println("APIRESULT: " + apiResult)
        var apiResultTwo = viewModel.returnPokemonData().value
        var apiResultThree = viewModel.returnPokemonValues()
        var apiResultTEST = viewModel.getPokemonNames()

        setContent {
            var grassColor = Color.Green
            var fireColor = Color.Red
            var waterColor = Color.Blue
            var electricColor = Color.Yellow

            var types: List<String> = listOf("Test")
            //types = TODO

            var typeOne = listOf("Grass")
            var typeOneNew = listOf("Fire", "Grass")

            var chosenColor: Color
            Column() {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if(types[0] == "Grass"){
                        chosenColor = grassColor
                    }
                    else if(types[0] == "Fire"){
                        chosenColor = fireColor
                    }
                    else if(types[0] == "Water"){
                        chosenColor = waterColor
                    }
                    else{
                        chosenColor = electricColor
                    }
                    pokemonCardComposable("Bulbasaur", typeOne, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/1.svg", chosenColor)
                    pokemonCardComposable("Bulbasaur", typeOne, "TESTURL", chosenColor)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if(typeOneNew[0] == "Grass"){
                        chosenColor = grassColor
                    }
                    else{
                        chosenColor = fireColor
                    }
                    pokemonCardComposable("Bulbasaur", typeOneNew, "TESTURL", chosenColor)
                    pokemonCardComposable("Bulbasaur", typeOneNew, "TESTURL", chosenColor)
                }
                Text(apiResult.toString()/*!!*/)
                Text(apiResultTwo.toString())
                Text(apiResultThree)
                Text(apiResultTEST.toString())
            }
        }


        /*pokemonAPI.getPokemon().enqueue(object : Callback<List<Pokemon>>{
            override fun onResponse(call: Call<List<Pokemon>>, response: Response<List<Pokemon>>) {
                Log.d("SUCCESS", response.body()?.toString() ?: "")
            }

            override fun onFailure(call: Call<List<Pokemon>>, t: Throwable) {
                Log.d("ERROR", t.toString())
            }

        })*/



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
        pokemonCardComposable("Bulbasaur", listOf("Grass"), "https://oyster.ignimgs.com/mediawiki/apis.ign.com/pokemon-x-y-version/5/51/Bulbasaur.jpg?width=325", Color.Red)
    }
}
