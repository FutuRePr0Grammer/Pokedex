package com.example.pokedex.data.data.dto

data class VersionGroupDetail(
    val level_learned_at: Int,
    val move_learn_method: com.example.pokedex.data.data.dto.MoveLearnMethod,
    val version_group: com.example.pokedex.data.data.dto.VersionGroup
)