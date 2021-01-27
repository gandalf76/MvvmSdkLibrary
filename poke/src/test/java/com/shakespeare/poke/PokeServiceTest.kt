package com.shakespeare.poke

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shakespeare.poke.sdk.model.Sprite
import com.shakespeare.poke.sdk.model.Sprites
import com.shakespeare.poke.sdk.service.PokeService
import com.shakespeare.poke.sdk.service.PokeServiceFactory
import com.shakespeare.poke.sdk.service.PokeServiceImpl
import com.shakespeare.poke.sdk.service.PokeServiceImpl.Companion.buildClass
import com.shakespeare.poke.sdk.service.api.PokemonApiService
import com.shakespeare.poke.sdk.service.api.ShakespeareApiService
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PokeServiceTest {

    @Mock
    private lateinit var mockPokemonApiService: PokemonApiService

    @Mock
    private lateinit var mockShakespeareApiService: ShakespeareApiService

    private lateinit var pokemonService: PokeService

    @Before
    fun setUp() {
        pokemonService = buildClass(mockPokemonApiService, mockShakespeareApiService)
    }

    @Test
    fun test_get_pokemon_shakespearean_translation()  {
        runBlocking {
            whenever(mockPokemonApiService.getPokemon(ArgumentMatchers.anyString())).thenReturn(MockUtils.getPokemon())
            whenever(mockShakespeareApiService.getDescription(any())).thenReturn(MockUtils.getDescription())

            val translatedDescription = pokemonService.getPokemonShakespeareanDescription(ArgumentMatchers.anyString())

            Assert.assertEquals(MockUtils.TRANSLATED , translatedDescription)
        }
    }

    @Test
    fun test_get_pokemon_shakespearean_translation_not_found()  {
        runBlocking {
            whenever(mockPokemonApiService.getPokemon(ArgumentMatchers.anyString())).thenReturn(MockUtils.getPokemonNoTranslation())

            val translatedDescription = pokemonService.getPokemonShakespeareanDescription(ArgumentMatchers.anyString())

            verify(mockShakespeareApiService, never()).getDescription(any())

            Assert.assertEquals(null , translatedDescription)
        }
    }

    @Test(expected = Exception::class)
    fun test_get_pokemon_shakespearean_translation_throw_exception()  {
        runBlocking {
            whenever(mockShakespeareApiService.getDescription(any())).thenThrow(Exception())
            pokemonService.getPokemonShakespeareanDescription(ArgumentMatchers.anyString())
        }
    }

    @Test
    fun test_get_pokemon_sprites_urls_front_sprite()  {
        runBlocking {
            whenever(mockPokemonApiService.getSprites(ArgumentMatchers.anyString())).thenReturn(MockUtils.getSprites())

            val sprite = pokemonService.getPokemonSpritesUrls(ArgumentMatchers.anyString())
            Assert.assertEquals(MockUtils.getSprites().sprites.front_default , sprite)
        }
    }

    @Test
    fun test_get_pokemon_sprites_urls_back_sprite()  {
        val backSprite = Sprites(Sprite(MockUtils.BACK_SPRITE, null))
        runBlocking {
            whenever(mockPokemonApiService.getSprites(ArgumentMatchers.anyString())).thenReturn(backSprite)

            val sprite = pokemonService.getPokemonSpritesUrls(ArgumentMatchers.anyString())
            Assert.assertEquals(MockUtils.getSprites().sprites.back_default , sprite)
        }
    }

    @Test
    fun test_create_poke_service_factory() {
        val factory = PokeServiceFactory.createPokeService()
        Assert.assertTrue(factory is PokeServiceImpl)
    }
}