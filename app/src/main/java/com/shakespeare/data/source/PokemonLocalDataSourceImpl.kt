package com.shakespeare.data.source

import android.database.sqlite.SQLiteConstraintException
import com.shakespeare.data.room.Pokemon
import com.shakespeare.data.room.PokemonDao

class PokemonLocalDataSourceImpl(private val pokemonDao: PokemonDao) : PokemonLocalDataSource {

    override suspend fun addToFavorite(pokemon: Pokemon): Boolean {
        return try {
            pokemonDao.insert(pokemon)
            true
        } catch (e: SQLiteConstraintException) {
            false
        }
    }

    override suspend fun getFavorites(): List<Pokemon> = pokemonDao.getAll()

    override suspend fun getFavoriteByName(name: String): Pokemon? {
        val pokemon = pokemonDao.getByName(name)
        return pokemon
    }
}