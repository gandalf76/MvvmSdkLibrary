package com.shakespeare.repository

import com.shakespeare.data.room.Pokemon

interface PokemonRepository {

    suspend fun getShakespeareanTranslation(name: String): String?

    suspend fun getPokemonImage(pokemonName: String) : String?

    suspend fun addToFavorite(name: String): Boolean

    suspend fun getFavorites(): List<Pokemon>

    suspend fun getFavoriteByName(name: String): Pokemon?
}