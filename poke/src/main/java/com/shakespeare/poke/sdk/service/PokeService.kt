package com.shakespeare.poke.sdk.service

interface PokeService {

    suspend fun getPokemonShakespeareanDescription(pokeName: String) : String?

    suspend fun getPokemonSpritesUrls(pokeName: String): String?
}