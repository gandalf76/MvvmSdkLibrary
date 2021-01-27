package com.shakespeare.data.source

import com.shakespeare.poke.sdk.service.PokeService

class PokemonRemoteDataSourceImpl(private val pokemonApi: PokeService): PokemonRemoteDataSource {

    override suspend fun getShakespeareanTranslation(name: String): String? {
        return pokemonApi.getPokemonShakespeareanDescription(name)
    }


    override suspend fun getPokemonImage(pokemonName: String): String? = pokemonApi.getPokemonSpritesUrls(pokemonName)

}