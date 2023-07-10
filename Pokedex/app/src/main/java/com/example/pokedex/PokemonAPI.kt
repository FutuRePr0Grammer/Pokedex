package com.example.pokedex

import com.example.pokedex.network.dto.PokemonDetailResponseDto
import com.example.pokedex.network.dto.PokemonListResponseDto

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonAPI {
    @GET("pokemon")
    fun getPokemon(): Call<PokemonListResponseDto>

    @GET("pokemon/{id}")
    fun getPokemonDetails(@Path("id") pokemonId: String): Call<PokemonDetailResponseDto>
}