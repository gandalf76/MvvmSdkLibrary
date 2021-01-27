package com.shakespeare.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shakespeare.data.room.Pokemon
import com.shakespeare.repository.PokemonRepository
import kotlinx.coroutines.launch

class PokemonViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {

    val translationViewState = MutableLiveData<String>()

    val pokemonImageViewState = MutableLiveData<String>()

    val addToFavoritesViewState = MutableLiveData<Boolean>()

    val showLoadingViewState = MutableLiveData<Boolean>()

    val checkFavoriteViewState = MutableLiveData<Boolean>()

    fun loadShakesperianTranslationAndPokemonImage(pokemonName: String) {
        viewModelScope.launch {
            showLoadingViewState.value = true
            val translation = pokemonRepository.getShakespeareanTranslation(pokemonName)
            val imageUrl = pokemonRepository.getPokemonImage(pokemonName)
            translationViewState.value = translation
            pokemonImageViewState.value = imageUrl
            showLoadingViewState.value = false
        }
    }

    fun addToFavorites(name: String) {
        viewModelScope.launch {
            val added = pokemonRepository.addToFavorite(name)
            addToFavoritesViewState.value = added
        }
    }

    fun checkFavorite(name: String) {
        viewModelScope.launch {
            val pokemon = pokemonRepository.getFavoriteByName(name)
            pokemon?.let {
                checkFavoriteViewState.value = true
            } ?: kotlin.run {
                checkFavoriteViewState.value = false
            }
        }
    }
}