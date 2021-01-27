package com.shakespeare

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shakespeare.data.room.Pokemon
import com.shakespeare.repository.PokemonRepository
import com.shakespeare.viewmodel.FavoritesViewModel
import com.shakespeare.viewmodel.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavoritesViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var pokemonRepository: PokemonRepository

    @Mock
    private lateinit var translationObserver: Observer<String>

    @Mock
    private lateinit var favoritesObserver: Observer<List<String>>

    @Captor
    private lateinit var translationCaptor: ArgumentCaptor<String>

    @Captor
    private lateinit var favoritesCaptor: ArgumentCaptor<List<String>>

    private lateinit var viewModel: FavoritesViewModel

    private val mockName = "fakeName"

    @Before
    fun setUp() {
        viewModel = FavoritesViewModel(pokemonRepository)
    }

    @Test
    fun test_search_shakesperian_translation_on_changed() {
        testCoroutineRule.runBlockingTest {
            viewModel.translationViewState.observeForever(translationObserver)
            viewModel.searchShakesperianTraslation(mockName)
            verify(translationObserver).onChanged(translationCaptor.capture())
            viewModel.translationViewState.removeObserver(translationObserver)

            Assert.assertEquals(mockName, translationCaptor.value)
        }
    }

    @Test
    fun test_load_favorites_onchanged() {
        testCoroutineRule.runBlockingTest {
            whenever(pokemonRepository.getFavorites()).thenReturn(listOf(Pokemon(mockName)))

            viewModel.favoritesViewState.observeForever(favoritesObserver)
            viewModel.loadFavorites()

            verify(favoritesObserver).onChanged(favoritesCaptor.capture())
            viewModel.favoritesViewState.removeObserver(favoritesObserver)

            Assert.assertEquals(1, favoritesCaptor.value.size)
            Assert.assertEquals(mockName, favoritesCaptor.value[0])
        }
    }
}