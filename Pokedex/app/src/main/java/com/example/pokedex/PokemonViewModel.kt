package com.example.pokedex

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

data class ViewState(
    val pokemonNames: List<String> = listOf(),
    val pokemonTypes: List<String> = listOf(),
    val pokemonImageUrls: List<String> = listOf()
)

class PokemonViewModel: ViewModel() {
    @Inject
    lateinit var pokemonAPI: PokemonAPI

    var viewState: ViewState by mutableStateOf(ViewState())
        init{
            RetrofitApplication.instance.retrofitComponent.inject(this)
            getPokemon()
            getPokemonDetails()
        }

    private fun getPokemon(){

        pokemonAPI.getPokemon().enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                Log.d("SUCCESS", response.body()?.toString() ?: "")
                viewState = viewState.copy(pokemonNames = response.body()?.results!!.map { it.name ?: "" })
                //Log.d("NAMES", viewState.pokemonNames.toString())
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                Log.d("ERROR", t.toString())
            }

        })
    }

    private fun getPokemonDetails(){
        var pokemonImages: ArrayList<String> = arrayListOf()
        for (increment in 1..20){
            pokemonAPI.getPokemonDetails(increment.toString()).enqueue(object : Callback<PokemonDetails> {
                override fun onResponse(call: Call<PokemonDetails>, response: Response<PokemonDetails>) {
                    Log.d("SUCCESS", response.body()?.toString() ?: "")
                    viewState = viewState.copy(pokemonTypes = response.body()?.types!!.map { it.type.name ?: "" })
                    /*pokemonImages.add(response.body()?.sprites!!.front_default)
                    viewState = viewState.copy(pokemonImageUrls = pokemonImages)*/
                    //TODO: below is needed, but Sprites can't use .map
                    viewState = viewState.copy(pokemonImageUrls = response.body()?.sprites!!.map {it.front_default ?: ""})
                    Log.d("Types", viewState.pokemonTypes.toString())
                    Log.d("Images", viewState.pokemonImageUrls.toString())
                }

                override fun onFailure(call: Call<PokemonDetails>, t: Throwable) {
                    Log.d("ERROR", t.toString())
                }

            })
        }

    }

}



