package com.shakespeare.poke.sdk.service

import com.shakespeare.poke.sdk.service.PokeServiceImpl.Companion.buildClass
import com.shakespeare.poke.sdk.service.api.PokemonApiService
import com.shakespeare.poke.sdk.service.api.RetrofitBuilder
import com.shakespeare.poke.sdk.service.api.ShakespeareApiService

object PokeServiceFactory {

    fun createPokeService(): PokeService {
        val pokemonService: PokemonApiService = RetrofitBuilder.POKEMON_API_SERVICE
        val shakespeareService: ShakespeareApiService = RetrofitBuilder.SHAKESPEARE_API_SERVICE
        return buildClass(pokemonService, shakespeareService)
    }
}