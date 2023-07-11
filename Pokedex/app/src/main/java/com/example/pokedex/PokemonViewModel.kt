package com.example.pokedex

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
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
    val pokemonId: String,
    var selectedColor: Color
) {
    val displayId get() = when (pokemonId.length) {
        1 -> "#00$pokemonId"
        2 -> "#0$pokemonId"
        else -> "#$pokemonId"
    }
    val capitalizedName get() = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
}

class PokemonViewModel: ViewModel() {
    @Inject
    lateinit var pokemonAPI: PokemonAPI

    val FIRE = Color(0xffF76351)
    val FIRE_LIGHT = Color(0xffF3978C)
    val WATER = Color(0xff4A90E2)
    val WATER_LIGHT = Color(0xff6FA3E1)
    val GRASS = Color(0xff21D380)
    val GRASS_LIGHT = Color(0xff62E0A5)
    val ELECTRIC = Color(0xffF7CC1A)
    val ELECTRIC_LIGHT = Color(0xffF9D954)

    val DARK = Color(0xff333333)
    val DARK_LIGHT = Color(0xff555555)

    val ROCK = Color(0xff5E3023)
    val ROCK_LIGHT = Color(0xff7F5447)
    val GROUND = Color(0xffC08552)
    val GROUND_LIGHT = Color(0xffD8AC86)
    val POISON = Color(0xff9448BC)
    val POISON_LIGHT = Color(0xffA974C5)

    val GHOST = Color(0xff4B3F72)
    val GHOST_LIGHT = Color(0xff655698)
    val PSYCHIC = Color(0xffDA4167)
    val PSYCHIC_LIGHT = Color(0xffFF6F93)
    val ICE = Color(0xff90FCF9)
    val ICE_LIGHT = Color(0xffCFFFFE)

    val STEEL = Color(0xff717C89)
    val STEEL_LIGHT = Color(0xff909FB0)
    val FIGHTING = Color(0xff841C26)
    val FIGHTING_LIGHT = Color(0xff9C535A)
    val FLYING = Color(0xff8ACDEA)
    val FLYING_LIGHT = Color(0xffA2E1FC)

    val DRAGON = Color(0xff725AC1)
    val DRAGON_LIGHT = Color(0xff8D79D1)
    val FAIRY = Color(0xffFF579F)
    val FAIRY_LIGHT = Color(0xffFF7EB5)
    val NORMAL = Color(0xff878E99)
    val NORMAL_LIGHT = Color(0xffAFB6C1)
    val BUG = Color(0xffBAB700)
    val BUG_LIGHT = Color(0xffCECB12)

    val UNKNOWN = Color(0xff333333)
    val UNKOWN_LIGHT = Color(0xff555555)


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
                        image = response.body()?.sprites?.other?.officialArtwork?.frontDefault ?: "",
                        pokemonId = response.body()?.id.toString(),
                        selectedColor = Color.Black

                    )

                    pokemon = mutableListOf<PokemonDetail>().apply {
                        addAll(pokemon)
                        add(data)
                    }

                    Log.d("TYPE: ", data.type.toString())

                    pokemon.forEach {pokemon ->
                        if(pokemon.type[0] == "grass"){
                            pokemon.selectedColor = GRASS_LIGHT
                        }
                        else if(pokemon.type[0] == "fire"){
                            pokemon.selectedColor = FIRE_LIGHT
                        }
                        else if(pokemon.type[0] == "poison"){
                            pokemon.selectedColor = POISON_LIGHT
                        }
                        else if(pokemon.type[0] == "water"){
                            pokemon.selectedColor = WATER_LIGHT
                        }
                        else if(pokemon.type[0] == "electric"){
                            pokemon.selectedColor = ELECTRIC_LIGHT
                        }
                        else if(pokemon.type[0] == "bug"){
                            pokemon.selectedColor = BUG_LIGHT
                        }
                        else{
                            pokemon.selectedColor = NORMAL_LIGHT
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
