package com.shakespeare

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.shakespeare.data.room.Pokemon
import com.shakespeare.data.room.PokemonRoomDatabase
import junit.framework.Assert.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

class PokemonDatabaseTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val database = Room.inMemoryDatabaseBuilder(context, PokemonRoomDatabase::class.java).build()

    private val MOCK_NAME = "fake_name"

    private val MOCK_NAME_2 = "fake_name_2"

    @Test
    fun setUp() {
        runBlocking {
            database.pokemonDao()?.delete(Pokemon(MOCK_NAME))
        }
    }

    @Test
    fun test_insert() {
        runBlocking {
            database.pokemonDao()?.insert(Pokemon(MOCK_NAME))
            val pokemon = database.pokemonDao()?.getByName(MOCK_NAME)

            assertNotNull(pokemon)
            assertEquals(MOCK_NAME, pokemon?.name)
        }
    }

    @Test(expected = SQLiteConstraintException::class)
    fun test_insert_duplicated() {
        runBlocking {
            database.pokemonDao()?.insert(Pokemon(MOCK_NAME))
            database.pokemonDao()?.insert(Pokemon(MOCK_NAME))
        }
    }

    @Test
    fun test_delete() {
        runBlocking {
            database.pokemonDao()?.insert(Pokemon(MOCK_NAME))
            database.pokemonDao()?.delete(Pokemon(MOCK_NAME))
            val pokemon = database.pokemonDao()?.getByName(MOCK_NAME)

            assertNull(pokemon)
        }
    }

    @Test
    fun test_get_all() {
        runBlocking {
            database.pokemonDao()?.insert(Pokemon(MOCK_NAME))
            database.pokemonDao()?.insert(Pokemon(MOCK_NAME_2))
            val pokemons = database.pokemonDao()?.getAll()

            assertEquals(2, pokemons?.size)
        }
    }

    @Test
    fun test_get_by_name() {
        runBlocking {
            database.pokemonDao()?.insert(Pokemon(MOCK_NAME))
            val pokemon = database.pokemonDao()?.getByName(MOCK_NAME)

            assertNotNull(pokemon)
            assertEquals(MOCK_NAME, pokemon?.name)
        }
    }
}