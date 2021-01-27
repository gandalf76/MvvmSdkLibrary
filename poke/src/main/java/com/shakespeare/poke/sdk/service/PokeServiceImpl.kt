package com.shakespeare.poke.sdk.service

import com.shakespeare.poke.sdk.model.PokemonBody
import com.shakespeare.poke.sdk.service.api.PokemonApiService
import com.shakespeare.poke.sdk.service.api.ShakespeareApiService
import kotlin.Exception

internal class PokeServiceImpl private constructor(private val pokemonService: PokemonApiService, private val shakespeareService: ShakespeareApiService) : PokeService {

    companion object {
        fun buildClass(pokemonService: PokemonApiService, shakespeareService: ShakespeareApiService) = PokeServiceImpl(pokemonService, shakespeareService)
    }

    /**
     * The skeakesperean service API allows a maximum of 5 times calls an a hour; if the limit exceeds,
     * a http 429 error EXCEPTION message will be returned from [ShakespeareApiService.getDescription]
     * @return the shakesperian translated string. If an error occours, return null.
     */
    override suspend fun getPokemonShakespeareanDescription(pokeName: String): String? {
        try {
            val entries = pokemonService.getPokemon(pokeName).flavor_text_entries
            entries.let {
                return if (it.isNotEmpty()) {
                    val translation = shakespeareService.getDescription(PokemonBody(it[0].flavor_text)).contents.translated
                    translation
                } else {
                    return null
                }
            }
        } catch (e: Exception) {
            return null
        }
    }

    override suspend fun getPokemonSpritesUrls(pokeName: String): String? {
        try {
            val sprites = pokemonService.getSprites(pokeName)
            return sprites.sprites.front_default ?: return sprites.sprites.back_default
        } catch (e: Exception) {
            return null
        }
    }
}