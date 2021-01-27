package com.shakespeare.poke.sdk.service.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object RetrofitBuilder {

    private const val POKE_BASE_URL = "https://pokeapi.co/api/v2/"

    private const val SHAKESPEARE_BASE_URL = "https://api.funtranslations.com/"

    private fun getPokemonRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(POKE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun getShakespeareRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(SHAKESPEARE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val POKEMON_API_SERVICE: PokemonApiService = getPokemonRetrofit().create(PokemonApiService::class.java)

    val SHAKESPEARE_API_SERVICE: ShakespeareApiService = getShakespeareRetrofit().create(ShakespeareApiService::class.java)
}