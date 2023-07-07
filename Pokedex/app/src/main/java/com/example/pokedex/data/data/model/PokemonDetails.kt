package com.example.pokedex.data.data.model

data class PokemonDetails(
    val abilities: List<com.example.pokedex.data.data.dto.Ability>,
    val base_experience: Int,
    val forms: List<com.example.pokedex.data.data.dto.Form>,
    val game_indices: List<com.example.pokedex.data.data.dto.GameIndice>,
    val height: Int,
    val held_items: List<Any>,
    val id: Int,
    val is_default: Boolean,
    val location_area_encounters: String,
    val moves: List<com.example.pokedex.data.data.dto.Move>,
    val name: String,
    val order: Int,
    val past_types: List<Any>,
    val species: com.example.pokedex.data.data.dto.Species,
    val sprites: com.example.pokedex.data.data.dto.Sprites,
    val stats: List<com.example.pokedex.data.data.dto.Stat>,
    val types: List<com.example.pokedex.data.data.dto.Type>,
    val weight: Int
)