package com.shakespeare

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import com.shakespeare.data.room.Pokemon
import com.shakespeare.data.source.PokemonLocalDataSource
import com.shakespeare.data.source.PokemonRemoteDataSource
import com.shakespeare.repository.PokemonRepository
import com.shakespeare.repository.PokemonRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PokemonRepositoryImplTest {

    @Mock
    private lateinit var mockPokemonRemoteDataSource: PokemonRemoteDataSource

    @Mock
    private lateinit var mockPokemonLocalDataSource: PokemonLocalDataSource

    private val MOCK_NAME = "fake_name"

    private lateinit var repo: PokemonRepository

    @Before
    fun setUp() {
        repo = PokemonRepositoryImpl(mockPokemonRemoteDataSource, mockPokemonLocalDataSource)
    }

    @Test
    fun test_get_pokemon_shakespearean_translation() = runBlocking {
        whenever(mockPokemonRemoteDataSource.getShakespeareanTranslation(ArgumentMatchers.anyString())).thenReturn(MockUtils.getDescription().contents.translated)

        val translation = repo.getShakespeareanTranslation(MOCK_NAME)

        Assert.assertEquals(MockUtils.TRANSLATED , translation)
    }

    @Test
    fun test_get_pokemon_sprites_urls()  {
        runBlocking {
            whenever(mockPokemonRemoteDataSource.getPokemonImage(ArgumentMatchers.anyString())).thenReturn(MockUtils.FRONT_SPRITE)

            val sprite = repo.getPokemonImage("url")
            Assert.assertEquals(MockUtils.FRONT_SPRITE, sprite)
        }
    }

    @Test
    fun test_add_to_favorites() {
        runBlocking {
            whenever(mockPokemonLocalDataSource.addToFavorite(any())).thenReturn(true)

            val result = repo.addToFavorite(MOCK_NAME)
            Assert.assertTrue(result)
        }
    }

    @Test
    fun test_add_to_favorites_fails() {
        runBlocking {
            whenever(mockPokemonLocalDataSource.addToFavorite(any())).thenReturn(false)

            val result = repo.addToFavorite(MOCK_NAME)
            Assert.assertFalse(result)
        }
    }

    @Test
    fun test_get_favorites() {
        runBlocking {
            whenever(mockPokemonLocalDataSource.getFavorites()).thenReturn(listOf(Pokemon(MOCK_NAME)))

            val result = repo.getFavorites()
            Assert.assertEquals(1, result.size)
            Assert.assertEquals(MOCK_NAME, result[0].name)
        }
    }

    @Test
    fun test_get_favorite_by_name() {
        runBlocking {
            whenever(mockPokemonLocalDataSource.getFavoriteByName(ArgumentMatchers.anyString())).thenReturn(Pokemon(MOCK_NAME))
            val result = repo.getFavoriteByName(MOCK_NAME)

            Assert.assertNotNull(result)
            Assert.assertEquals(MOCK_NAME, result?.name)
        }
    }

    @Test
    fun test_get_favorite_by_name_not_found() {
        runBlocking {
            whenever(mockPokemonLocalDataSource.getFavoriteByName(ArgumentMatchers.anyString())).thenReturn(null)
            val result = repo.getFavoriteByName(MOCK_NAME)

            Assert.assertNull(result)
        }
    }
}