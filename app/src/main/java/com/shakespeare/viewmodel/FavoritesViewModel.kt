package com.shakespeare.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shakespeare.repository.PokemonRepository
import kotlinx.coroutines.launch

class FavoritesViewModel(private val repository: PokemonRepository) : ViewModel() {

    val translationViewState = SingleLiveEvent<String>()

    val favoritesViewState = MutableLiveData<List<String>>()

    fun loadFavorites() {
        viewModelScope.launch {
            val favorites = repository.getFavorites().map {
                it.name
            }
            favoritesViewState.value = favorites
        }
    }

    fun searchShakesperianTraslation(name: String) {
        translationViewState.value = name
    }
}