package com.shakespeare

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.shakespeare.data.room.PokemonRoomDatabase
import com.shakespeare.data.source.PokemonLocalDataSourceImpl
import com.shakespeare.data.source.PokemonRemoteDataSourceImpl
import com.shakespeare.databinding.SearchFragmentBinding
import com.shakespeare.poke.sdk.service.PokeServiceFactory
import com.shakespeare.repository.PokemonRepositoryImpl
import com.shakespeare.viewmodel.SearchViewModel
import com.shakespeare.viewmodel.ViewModelFactory

class SearchFragment : Fragment() {

    private lateinit var binding: SearchFragmentBinding

    private lateinit var viewModel: SearchViewModel

    private var listener: FragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        listener?.onUpdateToolbar(false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupViewModel()
        setupObservers()
        setupListeners()
    }

    private fun setupViewModel() {
        val dao = PokemonRoomDatabase.getDatabase(requireContext().applicationContext)?.pokemonDao()
                ?: throw NullPointerException("Pokemon Dao is null")
        val repo = PokemonRepositoryImpl(PokemonRemoteDataSourceImpl(PokeServiceFactory.createPokeService()), PokemonLocalDataSourceImpl(dao))
        viewModel = ViewModelProvider(viewModelStore, ViewModelFactory(repo)).get(SearchViewModel::class.java)
    }

    private fun setupListeners() {
        binding.apply {
            search.setOnClickListener {
                val name = pokemonName.text.toString()
                viewModel.searchShakesperianTraslation(name)
            }
            favorites.setOnClickListener {
                viewModel.viewFavorites()
            }
        }
    }

    private fun setupObservers() {
        viewModel.apply {
            translationViewState.observe(viewLifecycleOwner, {
                val params = bundleOf(Pair(PokemonFragment.ARG_NAME, it))
                findNavController().navigate(R.id.action_searchFragment_to_pokemonFragment, params)
            })
            favoritesViewState.observe(viewLifecycleOwner, {
                findNavController().navigate(R.id.action_searchFragment_to_favoritesFragment)
            })
        }
    }
}