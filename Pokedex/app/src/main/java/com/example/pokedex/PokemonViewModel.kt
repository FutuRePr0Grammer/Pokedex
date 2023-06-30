package com.example.pokedex

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PokemonViewModel: ViewModel() {
    @Inject
    lateinit var pokemonAPI: PokemonAPI
    private var pokemonData = MutableLiveData<ArrayList<Results>>()
    fun getPokemon() {
        RetrofitApplication.instance.retrofitComponent.inject(this)
        var apiResult: String
        pokemonAPI.getPokemon().enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                Log.d("SUCCESS", response.body()?.toString() ?: "")
                apiResult = response.body()?.results.toString()
                Log.d("APIRESULT: ", apiResult/*!!*/)
                pokemonData.value = response.body()!!.results
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                Log.d("ERROR", t.toString())
            }

        })
    }

    fun returnPokemonData(): LiveData<ArrayList<Results>> {
        return pokemonData
    }
}