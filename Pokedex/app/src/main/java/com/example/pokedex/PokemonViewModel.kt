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
                //TODO: need postValue instead?
                pokemonData.value = response.body()!!.results
                //pokemonData.postValue(response.body()!!.results)
                Log.d("VALUERESULT: ", pokemonData.value.toString())
                setPokemonNames(pokemonData.value!!)
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                Log.d("ERROR", t.toString())
            }

        })
    }

    //TODO: pokemonData is null once it leaves the scope of the enqueue retrofit call
    //TODO: Don't pass API call, just get strings and pass them to view instead.
        //No logic should go in View related to parsing api data.
    //TODO: the reason it is null is because it is being called before the data is retrieved from API
    //TODO: must use callbacks to get data
    fun returnPokemonData(): LiveData<ArrayList<Results>> {
        println("POKEMONDATA: " + pokemonData.value.toString())
        println("POKEMONDATANOSTRING " + pokemonData.value)
        return pokemonData
    }

    fun returnPokemonValues(): String {
        return pokemonData.toString()
    }

    private var pokemonNames: ArrayList<Results>? = null
    fun setPokemonNames(pokemonData: ArrayList<Results>) {
        println("POKEMONDATAVALUE: " + pokemonData)
        pokemonNames = pokemonData
        println("POKEMON Names Value after SET: " + pokemonNames)
    }

    fun getPokemonNames(): ArrayList<Results>? {
        println("POKEMONNAMESVALUE: " + pokemonNames)
        return pokemonNames
    }
}



