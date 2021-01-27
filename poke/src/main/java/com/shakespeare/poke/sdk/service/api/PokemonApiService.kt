package com.shakespeare.poke.sdk.service.api

import com.shakespeare.poke.sdk.model.Pokemon
import com.shakespeare.poke.sdk.model.Sprites
import retrofit2.http.GET
import retrofit2.http.Path

internal interface PokemonApiService {

        @GET("pokemon-species/{name}/")
        suspend fun getPokemon(@Path("name") name: String): Pokemon

        @GET("pokemon/{name}/")
        suspend fun getSprites(@Path("name") name: String): Sprites
}