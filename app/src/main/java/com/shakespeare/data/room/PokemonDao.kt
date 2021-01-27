package com.shakespeare.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PokemonDao {
    @Insert
    suspend fun insert(pokemon: Pokemon)

    @Delete
    suspend fun delete(pokemon: Pokemon)

    @Query("SELECT * FROM Pokemon")
    suspend fun getAll(): List<Pokemon>

    @Query("SELECT * FROM Pokemon where name = :name")
    suspend fun getByName(name: String): Pokemon
}