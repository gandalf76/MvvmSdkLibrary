package com.shakespeare.repository

import com.shakespeare.data.room.Pokemon
import com.shakespeare.data.source.PokemonLocalDataSource
import com.shakespeare.data.source.PokemonRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonRepositoryImpl(private val remoteDataSource: PokemonRemoteDataSource, private val localDataSource: PokemonLocalDataSource): PokemonRepository {

    override suspend fun getShakespeareanTranslation(name: String): String? = withContext(Dispatchers.IO) {
        remoteDataSource.getShakespeareanTranslation(name)
    }

    override suspend fun getPokemonImage(pokemonName: String): String? = remoteDataSource.getPokemonImage(pokemonName)

    override suspend fun addToFavorite(name: String): Boolean =
        withContext(Dispatchers.IO) {
            localDataSource.addToFavorite(Pokemon(name))
        }

    override suspend fun getFavorites(): List<Pokemon> = withContext(Dispatchers.IO) {
        localDataSource.getFavorites()
    }

    override suspend fun getFavoriteByName(name: String): Pokemon? = withContext(Dispatchers.IO) {
        localDataSource.getFavoriteByName(name)
    }
}