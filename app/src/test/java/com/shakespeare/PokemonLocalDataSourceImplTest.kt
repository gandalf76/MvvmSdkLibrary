package com.shakespeare

import android.database.sqlite.SQLiteConstraintException
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import com.shakespeare.data.room.Pokemon
import com.shakespeare.data.room.PokemonDao
import com.shakespeare.data.source.PokemonLocalDataSource
import com.shakespeare.data.source.PokemonLocalDataSourceImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PokemonLocalDataSourceImplTest {

    @Mock
    private lateinit var mockPokemonDao: PokemonDao

    private val MOCK_NAME = "fake_name"

    private lateinit var localDataSource: PokemonLocalDataSource

    @Before
    fun setUp() {
        localDataSource = PokemonLocalDataSourceImpl(mockPokemonDao)
    }

    @Test
    fun test_add_to_favorite() = runBlocking {
        whenever(mockPokemonDao.insert(any())).thenReturn(Unit)
        val result = localDataSource.addToFavorite(Pokemon(MOCK_NAME))

        Assert.assertEquals(true , result)
    }

    @Test
    fun test_add_to_favorite_fails() = runBlocking {
        whenever(mockPokemonDao.insert(any())).thenThrow(SQLiteConstraintException::class.java)
        val result = localDataSource.addToFavorite(Pokemon((MOCK_NAME)))

        Assert.assertEquals(false , result)
    }

    @Test
    fun test_get_favorites() = runBlocking {
        whenever(mockPokemonDao.getAll()).thenReturn(listOf(Pokemon(MOCK_NAME)))
        val result = localDataSource.getFavorites()

        Assert.assertEquals(1 , result.size)
        Assert.assertEquals(MOCK_NAME , result[0].name)
    }

    @Test
    fun test_get_favorite_by_name() = runBlocking {
        whenever(mockPokemonDao.getByName(ArgumentMatchers.anyString())).thenReturn(Pokemon(MOCK_NAME))
        val result = localDataSource.getFavoriteByName(MOCK_NAME)

        Assert.assertNotNull(result)
        Assert.assertEquals(MOCK_NAME , result?.name)
    }
    @Test
    fun test_get_favorite_by_name_not_found() = runBlocking {
        whenever(mockPokemonDao.getByName(ArgumentMatchers.anyString())).thenReturn(null)
        val result = localDataSource.getFavoriteByName(MOCK_NAME)

        Assert.assertNull(result)
    }

}