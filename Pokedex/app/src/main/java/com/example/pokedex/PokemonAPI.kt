package com.example.pokedex

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonAPI {
    @GET("pokemon")
    fun getPokemon(): Call<Pokemon>

    @GET("pokemon/{id}")
    fun getPokemonDetails(@Path("id") pokemonId: String): Call<PokemonDetails>
}