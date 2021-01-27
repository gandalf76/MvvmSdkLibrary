package com.shakespeare.data.source

interface PokemonRemoteDataSource {

    suspend fun getShakespeareanTranslation(name: String): String?

    suspend fun getPokemonImage(pokemonName: String) : String?

}