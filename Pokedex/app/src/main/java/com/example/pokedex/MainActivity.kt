package com.example.pokedex

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var retrofitBuilder: Retrofit
    @Inject
    lateinit var interceptor: HttpLoggingInterceptor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }

        RetrofitApplication.instance.retrofitComponent.inject(this)

        val service = retrofitBuilder.create(PokemonAPI::class.java)

        service.getPokemon().enqueue(object : Callback<List<Pokemon>>{
            override fun onResponse(call: Call<List<Pokemon>>, response: Response<List<Pokemon>>) {
                Log.d("SUCCESS", response.body()?.toString() ?: "")
            }

            override fun onFailure(call: Call<List<Pokemon>>, t: Throwable) {
                Log.d("ERROR", t.toString())
            }

        })

        //RetrofitApplication.retrofitComponent.inject(this)

        /*val service = builder.create(PokemonAPI::class.java)

        //val call: Call<List<Pokemon>> = service.getPokemon()

        service.getPokemon().enqueue(object : Callback<List<Pokemon>> {
            override fun onResponse(call: Call<List<Pokemon>>, response: Response<List<Pokemon>>) {
                Log.d("SUCCESS", response.body()?.toString() ?: "")
            }

            override fun onFailure(call: Call<List<Pokemon>>, t: Throwable) {
                Log.d("ERROR", t.toString())
            }

        })*/


    }

}
