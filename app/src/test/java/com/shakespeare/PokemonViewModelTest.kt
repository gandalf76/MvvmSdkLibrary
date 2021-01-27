package com.shakespeare

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shakespeare.data.room.Pokemon
import com.shakespeare.repository.PokemonRepository
import com.shakespeare.viewmodel.PokemonViewModel
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
class PokemonViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var pokemonRepository: PokemonRepository

    @Mock
    private lateinit var translationObserver: Observer<String>

    @Mock
    private lateinit var imageObserver: Observer<String>

    @Mock
    private lateinit var showLoadingObserver: Observer<Boolean>

    @Mock
    private lateinit var checkFavoriteObserver: Observer<Boolean>

    @Mock
    private lateinit var addToFavoriteObserver: Observer<Boolean>

    @Captor
    private lateinit var translationCaptor: ArgumentCaptor<String>

    @Captor
    private lateinit var imageCaptor: ArgumentCaptor<String>

    @Captor
    private lateinit var showLoadingCaptor: ArgumentCaptor<Boolean>

    @Captor
    private lateinit var checkFavoriteCaptor: ArgumentCaptor<Boolean>

    @Captor
    private lateinit var addToFavoriteCaptor: ArgumentCaptor<Boolean>

    private lateinit var viewModel: PokemonViewModel

    private val mockName = "fakeName"

    @Before
    fun setUp() {
        viewModel = PokemonViewModel(pokemonRepository)
    }

    @Test
    fun test_load_shakesperian_translation_and_pokemon_image_on_changed() {
        testCoroutineRule.runBlockingTest {
            whenever(pokemonRepository.getShakespeareanTranslation(ArgumentMatchers.anyString())).thenReturn(MockUtils.TRANSLATION)
            whenever(pokemonRepository.getPokemonImage(ArgumentMatchers.anyString())).thenReturn(MockUtils.FRONT_SPRITE)

            viewModel.translationViewState.observeForever(translationObserver)
            viewModel.pokemonImageViewState.observeForever(imageObserver)
            viewModel.showLoadingViewState.observeForever(showLoadingObserver)

            viewModel.loadShakesperianTranslationAndPokemonImage(mockName)

            verify(showLoadingObserver, times(2)).onChanged(showLoadingCaptor.capture())
            Assert.assertEquals(true, showLoadingCaptor.allValues[0])
            Assert.assertEquals(false, showLoadingCaptor.allValues[1])

            verify(translationObserver).onChanged(translationCaptor.capture())
            verify(imageObserver).onChanged(imageCaptor.capture())

            viewModel.translationViewState.removeObserver(translationObserver)
            viewModel.pokemonImageViewState.removeObserver(imageObserver)

            Assert.assertEquals(MockUtils.TRANSLATION, translationCaptor.value)
            Assert.assertEquals(MockUtils.FRONT_SPRITE, imageCaptor.value)
        }
    }

    @Test
    fun test_load_shakesperian_translation_and_pokemon_image_error_retrieving_on_changed() {
        testCoroutineRule.runBlockingTest {
            whenever(pokemonRepository.getShakespeareanTranslation(ArgumentMatchers.anyString())).thenReturn(null)
            whenever(pokemonRepository.getPokemonImage(ArgumentMatchers.anyString())).thenReturn(null)

            viewModel.translationViewState.observeForever(translationObserver)
            viewModel.pokemonImageViewState.observeForever(imageObserver)
            viewModel.showLoadingViewState.observeForever(showLoadingObserver)

            viewModel.loadShakesperianTranslationAndPokemonImage(mockName)

            verify(showLoadingObserver, times(2)).onChanged(showLoadingCaptor.capture())
            Assert.assertEquals(true, showLoadingCaptor.allValues[0])
            Assert.assertEquals(false, showLoadingCaptor.allValues[1])

            verify(translationObserver).onChanged(translationCaptor.capture())
            verify(imageObserver).onChanged(imageCaptor.capture())

            viewModel.translationViewState.removeObserver(translationObserver)
            viewModel.pokemonImageViewState.removeObserver(imageObserver)

            Assert.assertEquals(null, translationCaptor.value)
            Assert.assertEquals(null, imageCaptor.value)
        }
    }

    @Test
    fun test_check_favorite_true_on_changed() {
        testCoroutineRule.runBlockingTest {
            whenever(pokemonRepository.getFavoriteByName(ArgumentMatchers.anyString())).thenReturn(Pokemon("fake_name"))

            viewModel.checkFavoriteViewState.observeForever(checkFavoriteObserver)

            viewModel.checkFavorite("fake_name")

            verify(checkFavoriteObserver).onChanged(checkFavoriteCaptor.capture())

            Assert.assertEquals(true, checkFavoriteCaptor.value)
        }
    }

    @Test
    fun test_check_favorite_false_on_changed() {
        testCoroutineRule.runBlockingTest {
            whenever(pokemonRepository.getFavoriteByName(ArgumentMatchers.anyString())).thenReturn(null)

            viewModel.checkFavoriteViewState.observeForever(checkFavoriteObserver)

            viewModel.checkFavorite("fake_name")

            verify(checkFavoriteObserver).onChanged(checkFavoriteCaptor.capture())

            Assert.assertEquals(false, checkFavoriteCaptor.value)
        }
    }

    @Test
    fun test_add_to_favorites_true_on_changed() {
        testCoroutineRule.runBlockingTest {
            whenever(pokemonRepository.addToFavorite(ArgumentMatchers.anyString())).thenReturn(true)

            viewModel.addToFavoritesViewState.observeForever(addToFavoriteObserver)

            viewModel.addToFavorites("fake_name")

            verify(addToFavoriteObserver).onChanged(addToFavoriteCaptor.capture())

            Assert.assertEquals(true, addToFavoriteCaptor.value)
        }
    }

    @Test
    fun test_add_to_favorites_false_on_changed() {
        testCoroutineRule.runBlockingTest {
            whenever(pokemonRepository.addToFavorite(ArgumentMatchers.anyString())).thenReturn(false)

            viewModel.addToFavoritesViewState.observeForever(addToFavoriteObserver)

            viewModel.addToFavorites("fake_name")

            verify(addToFavoriteObserver).onChanged(addToFavoriteCaptor.capture())

            Assert.assertEquals(false, addToFavoriteCaptor.value)
        }
    }
}