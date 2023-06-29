package com.example.pokedex

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var pokemonAPI: PokemonAPI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RetrofitApplication.instance.retrofitComponent.inject(this)
        setContent {
            Column() {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    pokemonCardComposable()
                    pokemonCardComposable()
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    pokemonCardComposable()
                    pokemonCardComposable()
                }
            }
        }


        pokemonAPI.getPokemon().enqueue(object : Callback<List<Pokemon>>{
            override fun onResponse(call: Call<List<Pokemon>>, response: Response<List<Pokemon>>) {
                Log.d("SUCCESS", response.body()?.toString() ?: "")
            }

            override fun onFailure(call: Call<List<Pokemon>>, t: Throwable) {
                Log.d("ERROR", t.toString())
            }

        })



    }
    @Composable
    fun pokemonCardComposable() {
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
                    .background(Color.Red)
                    .size(150.dp)
            ) {

                Column {
                    Text("Pokemon Name")
                    Text("Pokemon Type(s)")
                }
                Text("Image")
            }
        }
    }



    @Preview
    @Composable
    fun pokemonCardComposablePreview() {
        pokemonCardComposable()
    }
}
