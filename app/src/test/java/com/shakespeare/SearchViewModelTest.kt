package com.shakespeare

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.shakespeare.viewmodel.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var translationObserver: Observer<String>

    @Mock
    private lateinit var favoritesObserver: Observer<Boolean>

    @Captor
    private lateinit var translationCaptor: ArgumentCaptor<String>

    @Captor
    private lateinit var favoritesCaptor: ArgumentCaptor<Boolean>

    private lateinit var viewModel: SearchViewModel

    private val mockName = "fakeName"

    @Before
    fun setUp() {
        viewModel = SearchViewModel()
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
    fun test_view_favorites_onchanged() {
        testCoroutineRule.runBlockingTest {
            viewModel.favoritesViewState.observeForever(favoritesObserver)
            viewModel.viewFavorites()
            verify(favoritesObserver).onChanged(favoritesCaptor.capture())
            viewModel.favoritesViewState.removeObserver(favoritesObserver)

            Assert.assertEquals(true, favoritesCaptor.value)
        }
    }
}