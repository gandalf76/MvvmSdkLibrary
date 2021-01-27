package com.shakespeare.poke.sdk.model

data class ShakespeareTranslation(val success: Success, val contents: Contents)

data class Success(val total: Int)

data class Contents(val translated: String, val text: String, val translation: String)