package com.shakespeare.poke.sdk.service.api

import com.shakespeare.poke.sdk.model.PokemonBody
import com.shakespeare.poke.sdk.model.ShakespeareTranslation
import retrofit2.Call
import retrofit2.http.*
import java.util.*

internal interface ShakespeareApiService {

        @POST("translate/shakespeare")
        suspend fun getDescription(@Body text: PokemonBody): ShakespeareTranslation
}