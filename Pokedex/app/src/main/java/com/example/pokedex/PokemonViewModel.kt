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
    val pokemonNames: List<String> = listOf()
)

class PokemonViewModel: ViewModel() {
    @Inject
    lateinit var pokemonAPI: PokemonAPI

    var viewState: ViewState by mutableStateOf(ViewState())
        init{
            RetrofitApplication.instance.retrofitComponent.inject(this)
            getPokemon()
        }

    private fun getPokemon(){

        pokemonAPI.getPokemon().enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                Log.d("SUCCESS", response.body()?.toString() ?: "")
                viewState = viewState.copy(pokemonNames = response.body()?.results!!.map { it.name ?: "" })
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                Log.d("ERROR", t.toString())
            }

        })
    }

}



