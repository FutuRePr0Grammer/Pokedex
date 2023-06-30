package com.example.pokedex

import dagger.Component
import javax.inject.Singleton

@Component(modules = [RetrofitModule::class])
@Singleton
interface RetrofitComponent {
    fun inject(pokemonViewModel: PokemonViewModel)
}