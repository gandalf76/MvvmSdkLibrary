package com.shakespeare.poke.sdk.model

data class Pokemon(val id: Int, val name: String, val flavor_text_entries: List<FlavorTextEntry> = listOf())

data class PokemonBody(
    val text: String
)

data class FlavorTextEntry(val flavor_text: String, val language: Language, val version: Version)

data class Language(val name: String, val url: String)

data class Version(val name: String, val url: String)
