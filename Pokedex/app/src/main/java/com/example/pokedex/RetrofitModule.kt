package com.example.pokedex

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RetrofitModule {
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .client(okHttpClient)
            .build()
    }

    /*@Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): WebApi {
        return retrofit.create(WebApi::class.java)
    }*/

    @Singleton
    @Provides
    fun provideInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return loggingInterceptor
    }
}