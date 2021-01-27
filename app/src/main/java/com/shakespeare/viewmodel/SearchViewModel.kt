package com.shakespeare.viewmodel

import androidx.lifecycle.ViewModel

class SearchViewModel() : ViewModel() {

    val translationViewState = SingleLiveEvent<String>()

    val favoritesViewState = SingleLiveEvent<Boolean>()

    fun searchShakesperianTraslation(name: String) {
        translationViewState.value = name
    }

    fun viewFavorites() {
        favoritesViewState.value = true
    }
}