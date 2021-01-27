package com.shakespeare.data.source

import com.shakespeare.data.room.Pokemon

interface PokemonLocalDataSource {

    suspend fun addToFavorite(pokemon: Pokemon): Boolean

    suspend fun getFavorites() : List<Pokemon>

    suspend fun getFavoriteByName(name: String): Pokemon?
}