package com.example.pokedex

import com.example.pokedex.data.data.model.Pokemon
import com.example.pokedex.data.data.model.PokemonDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonAPI {
    @GET("pokemon")
    fun getPokemon(): Call<Pokemon>

    @GET("pokemon/{id}")
    fun getPokemonDetails(@Path("id") pokemonId: String): Call<PokemonDetails>
}