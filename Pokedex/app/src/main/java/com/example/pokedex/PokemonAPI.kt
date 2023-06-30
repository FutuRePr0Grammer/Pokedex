package com.example.pokedex

import retrofit2.Call
import retrofit2.http.GET

interface PokemonAPI {
    @GET("pokemon")
    fun getPokemon(): Call<Pokemon>
}