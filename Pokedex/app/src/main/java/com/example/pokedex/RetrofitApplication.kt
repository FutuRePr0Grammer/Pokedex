package com.example.pokedex

import android.app.Application

class RetrofitApplication : Application(){
    companion object {
        lateinit var instance: RetrofitApplication
            private set
    }

    lateinit var retrofitComponent: RetrofitComponent
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        retrofitComponent = DaggerRetrofitComponent.builder()
            .build()
    }

}