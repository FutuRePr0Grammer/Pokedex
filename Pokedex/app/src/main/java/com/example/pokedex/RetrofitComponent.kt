package com.example.pokedex

import com.example.pokedex.RetrofitModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [RetrofitModule::class])
@Singleton
interface RetrofitComponent {
    fun inject(classToInjectModuleInto: Any/*app: RetrofitApplication*/)
}