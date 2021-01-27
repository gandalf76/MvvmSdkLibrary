package com.shakespeare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shakespeare.data.room.PokemonRoomDatabase
import com.shakespeare.data.source.PokemonLocalDataSourceImpl
import com.shakespeare.data.source.PokemonRemoteDataSourceImpl
import com.shakespeare.databinding.FavoritesFragmentBinding
import com.shakespeare.poke.sdk.service.PokeServiceFactory
import com.shakespeare.repository.PokemonRepositoryImpl
import com.shakespeare.viewmodel.FavoritesViewModel
import com.shakespeare.viewmodel.ViewModelFactory

class FavoritesFragment : Fragment(), FavoritesAdapter.OnFavoriteClickListener {

    private lateinit var binding: FavoritesFragmentBinding

    private lateinit var viewModel: FavoritesViewModel

    private lateinit var adapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FavoritesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupViewModel()
        setupList()
        setupObservers()

        viewModel.loadFavorites()
    }

    private fun setupViewModel() {
        val dao = PokemonRoomDatabase.getDatabase(requireContext().applicationContext)?.pokemonDao()
                ?: throw NullPointerException("Pokemon Dao is null")
        val repo = PokemonRepositoryImpl(PokemonRemoteDataSourceImpl(PokeServiceFactory.createPokeService()), PokemonLocalDataSourceImpl(dao))
        viewModel = ViewModelProvider(viewModelStore, ViewModelFactory(repo)).get(FavoritesViewModel::class.java)
    }

    private fun setupList() {
        adapter = FavoritesAdapter(listener = this)
        binding.apply {
            favoritesList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            favoritesList.setHasFixedSize(true)
            favoritesList.adapter = adapter
        }
    }

    private fun setupObservers() {
        viewModel.apply {
            translationViewState.observe(viewLifecycleOwner, {
                val params = bundleOf(Pair(PokemonFragment.ARG_NAME, it))
                findNavController().navigate(R.id.action_favoritesFragment_to_pokemonFragment, params)
            })
            favoritesViewState.observe(viewLifecycleOwner, {
                if (it.isNotEmpty()) {
                    adapter.updateItem(it)
                    setFavoritesState(true)
                } else {
                    setFavoritesState(false)
                }
            })
        }
    }

    private fun setFavoritesState(show: Boolean) {
        binding.apply {
            if(show) {
                favoritesList.visibility = View.VISIBLE
                noFavourites.visibility = View.GONE
            } else {
                favoritesList.visibility = View.GONE
                noFavourites.visibility = View.VISIBLE
            }
        }
    }

    override fun onItemClicked(name: String) {
        viewModel.searchShakesperianTraslation(name)
    }
}