package com.shakespeare

import com.nhaarman.mockitokotlin2.whenever
import com.shakespeare.data.source.PokemonRemoteDataSource
import com.shakespeare.data.source.PokemonRemoteDataSourceImpl
import com.shakespeare.poke.sdk.model.Sprite
import com.shakespeare.poke.sdk.model.Sprites
import com.shakespeare.poke.sdk.service.PokeService
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PokemonRemoteDataSourceImplTest {

    @Mock
    private lateinit var mockPokeServiceImpl: PokeService

    private lateinit var remoteDataSource: PokemonRemoteDataSource

    @Before
    fun setUp() {
        remoteDataSource = PokemonRemoteDataSourceImpl(mockPokeServiceImpl)
    }

    @Test
    fun test_get_pokemon_shakespearean_translation() = runBlocking {
        whenever(mockPokeServiceImpl.getPokemonShakespeareanDescription(ArgumentMatchers.anyString())).thenReturn(MockUtils.getDescription().contents.translated)

        val translation = remoteDataSource.getShakespeareanTranslation(ArgumentMatchers.anyString())

        Assert.assertEquals(MockUtils.TRANSLATED , translation)
    }

    @Test
    fun test_get_pokemon_sprites_urls()  {
        runBlocking {
            whenever(mockPokeServiceImpl.getPokemonSpritesUrls(ArgumentMatchers.anyString())).thenReturn(MockUtils.FRONT_SPRITE)

            val sprite = remoteDataSource.getPokemonImage(ArgumentMatchers.anyString())
            Assert.assertEquals(MockUtils.FRONT_SPRITE, sprite)
        }
    }
}