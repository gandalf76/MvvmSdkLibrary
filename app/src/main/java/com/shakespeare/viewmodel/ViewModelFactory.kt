package com.shakespeare.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shakespeare.repository.PokemonRepository

class ViewModelFactory(private val pokemonRepository: PokemonRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel() as T
            }
            modelClass.isAssignableFrom(PokemonViewModel::class.java) -> {
                PokemonViewModel(pokemonRepository) as T
            }
            modelClass.isAssignableFrom(FavoritesViewModel::class.java) -> {
                FavoritesViewModel(pokemonRepository) as T
            }
            else -> {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }
}