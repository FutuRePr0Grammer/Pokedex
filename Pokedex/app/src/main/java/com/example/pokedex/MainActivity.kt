package com.example.pokedex

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

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
}
