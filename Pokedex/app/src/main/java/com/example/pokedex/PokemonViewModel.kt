package com.example.pokedex

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale
import javax.inject.Inject

data class ViewState(
    val pokemonNames: List<String> = listOf(),
    val pokemonTypes: List<String> = listOf(),
    val pokemonImageUrls: List<String> = listOf()
)

data class PokemonDetail(
    val name: String,
    val type: List<String>,
    val image: String
)

class PokemonViewModel: ViewModel() {
    @Inject
    lateinit var pokemonAPI: PokemonAPI

    var grassColor: Color = Color.Green
    var fireColor: Color = Color.Red
    var electricColor: Color = Color.Yellow
    var waterColor: Color = Color.Blue
    var poisonColor: Color = Color.Magenta
    var bugColor: Color = Color.Gray
    var normalColor: Color = Color.White


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
                //Log.d("NAMES", viewState.pokemonNames.toString())

                val pokemonList = response.body()?.results ?: listOf()
                getPokemonDetails(pokemonList)
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                Log.d("ERROR", t.toString())
            }

        })
    }

    var pokemon: List<PokemonDetail> by mutableStateOf(listOf())

    private fun getPokemonDetails(list: List<Results>){
        list.map {
            pokemonAPI.getPokemonDetails(it.name?.lowercase(Locale.ROOT) ?: "").enqueue(object : Callback<PokemonDetails> {
            override fun onResponse(
                call: Call<PokemonDetails>,
                response: Response<PokemonDetails>
            ) {
                val data = PokemonDetail(
                    name = response.body()?.name ?: "",
                    type = response.body()?.types?.map {it.type.name} ?: listOf(),
                    image = response.body()?.sprites?.front_default ?: ""

                )

                pokemon = mutableListOf<PokemonDetail>().apply {
                    addAll(pokemon)
                    add(data)
                }

                Log.d("TYPE: ", data.type.toString())

                /*if(data.type[0] == "grass"){
                    selectedColor = Color.Green
                }*/
            }

            override fun onFailure(call: Call<PokemonDetails>, t: Throwable) {
                Log.d("ERROR", t.toString())
            }

        })
        }


    }

}



