package com.shakespeare

import com.shakespeare.poke.sdk.model.*

object MockUtils {

    const val LANGUAGE_NAME = "language name"
    const val LANGUAGE_URL = "language url"
    const val VERSION_NAME = "version name"
    const val VERSION_URL = "version url"
    const val FLAVOR_TEXT = "flavor text to translate"
    const val POKE_NAME = "poke name"
    const val BACK_SPRITE = "back sprite"
    const val FRONT_SPRITE = "front sprite"
    const val TRANSLATION = "shakespeare"
    const val TRANSLATED = "translated text"
    const val TEXT = "text"

    fun getPokemon(): Pokemon {
        val language = Language(LANGUAGE_NAME, LANGUAGE_URL)
        val version = Version(VERSION_NAME, VERSION_URL)
        val flavorTextEntry = FlavorTextEntry(FLAVOR_TEXT, language, version)
        return Pokemon(1, POKE_NAME, listOf(flavorTextEntry))
    }

    fun getSprites(): Sprites = Sprites(Sprite(BACK_SPRITE, FRONT_SPRITE))

    fun getDescription(): ShakespeareTranslation {
        val success = Success(1)
        val contents = Contents(TRANSLATED, TEXT, TRANSLATION)
        return ShakespeareTranslation(success, contents)
    }

}