package com.example.pokedex

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.pokedex.network.dto.PokemonDetailResponseDto
import com.example.pokedex.network.dto.PokemonListResponseDto
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
    val image: String,
    var selectedColor: Color
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

        pokemonAPI.getPokemon().enqueue(object : Callback<PokemonListResponseDto> {
            override fun onResponse(call: Call<PokemonListResponseDto>, response: Response<PokemonListResponseDto>) {
                Log.d("SUCCESS", response.body()?.toString() ?: "")
                viewState = viewState.copy(pokemonNames = response.body()?.results!!.map { it?.name ?: "" })
                //Log.d("NAMES", viewState.pokemonNames.toString())

                val pokemonList = response.body()?.results?.mapNotNull { it } ?: listOf()
                getPokemonDetails(pokemonList)
            }

            override fun onFailure(call: Call<PokemonListResponseDto>, t: Throwable) {
                Log.d("ERROR", t.toString())
            }

        })
    }

    var pokemon: List<PokemonDetail> by mutableStateOf(listOf())

    private fun getPokemonDetails(list: List<PokemonListResponseDto.Result>){
        list.map {
            pokemonAPI.getPokemonDetails(it.name?.lowercase(Locale.ROOT) ?: "").enqueue(object : Callback<PokemonDetailResponseDto> {
            override fun onResponse(
                call: Call<PokemonDetailResponseDto>,
                response: Response<PokemonDetailResponseDto>
            ) {
                val data = PokemonDetail(
                    name = response.body()?.name ?: "",
                    type = response.body()?.types?.mapNotNull { it?.type?.name } ?: listOf(),
                    image = response.body()?.sprites?.frontDefault ?: "",
                    selectedColor = Color.Black

                )

                pokemon = mutableListOf<PokemonDetail>().apply {
                    addAll(pokemon)
                    add(data)
                }

                Log.d("TYPE: ", data.type.toString())

                pokemon.forEach {pokemon ->
                    if(pokemon.type[0] == "grass"){
                        pokemon.selectedColor = grassColor
                    }
                    else if(pokemon.type[0] == "fire"){
                        pokemon.selectedColor = fireColor
                    }
                    else if(pokemon.type[0] == "poison"){
                        pokemon.selectedColor = poisonColor
                    }
                    else if(pokemon.type[0] == "water"){
                        pokemon.selectedColor = waterColor
                    }
                    else if(pokemon.type[0] == "electric"){
                        pokemon.selectedColor = electricColor
                    }
                    else if(pokemon.type[0] == "bug"){
                        pokemon.selectedColor = bugColor
                    }
                    else{
                        pokemon.selectedColor = normalColor
                    }
                }
            }

            override fun onFailure(call: Call<PokemonDetailResponseDto>, t: Throwable) {
                Log.d("ERROR", t.toString())
            }

        })
        }


    }

}



