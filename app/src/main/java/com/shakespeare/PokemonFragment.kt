package com.shakespeare

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.shakespeare.data.room.PokemonRoomDatabase
import com.shakespeare.data.source.PokemonLocalDataSourceImpl
import com.shakespeare.data.source.PokemonRemoteDataSourceImpl
import com.shakespeare.databinding.PokemonFragmentBinding
import com.shakespeare.poke.sdk.service.PokeServiceFactory
import com.shakespeare.repository.PokemonRepositoryImpl
import com.shakespeare.viewmodel.PokemonViewModel
import com.shakespeare.viewmodel.ViewModelFactory

class PokemonFragment : Fragment() {

    private lateinit var binding: PokemonFragmentBinding

    private lateinit var name: String

    private lateinit var viewModel: PokemonViewModel

    private var listener: FragmentInteractionListener? = null

    private val ITEM_OK_TAG = 0

    private val ITEM_ALREADY_ADDED_TAG = 1

    private val ITEM_ERROR_TRANSLATION_TAG = 2

    companion object {
        const val ARG_NAME = "ARG_NAME"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            listener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getSerializable(ARG_NAME) as String
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PokemonFragmentBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        listener?.onUpdateToolbar(true)
        return binding.root
    }

    private fun addToFavorites() {
        viewModel.addToFavorites(name)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val dao = PokemonRoomDatabase.getDatabase(requireContext().applicationContext)?.pokemonDao() ?: throw NullPointerException("Pokemon Dao is null")
        val repo = PokemonRepositoryImpl(PokemonRemoteDataSourceImpl(PokeServiceFactory.createPokeService()), PokemonLocalDataSourceImpl(dao))
        viewModel = ViewModelProvider(viewModelStore, ViewModelFactory(repo)).get(PokemonViewModel::class.java)

        setupObservers()
        setupFabButton()
        setupListeners()
        viewModel.loadShakesperianTranslationAndPokemonImage(name)
    }

    private fun setupObservers() {
        viewModel.apply {
            translationViewState.observe(viewLifecycleOwner, {
                it?.let {
                    binding.detail.setDescription(it)
                    if (binding.fab.tag == null) {
                        binding.fab.tag = ITEM_OK_TAG
                    }
                } ?: kotlin.run {
                    binding.detail.setDescription(getString(R.string.error_retrieving_translation))
                    binding.fab.tag = ITEM_ERROR_TRANSLATION_TAG
                }

            })
            pokemonImageViewState.observe(viewLifecycleOwner, {
                binding.detail.setImage(it)
            })
            showLoadingViewState.observe(viewLifecycleOwner, {
                binding.detail.showLoading(it)
            })
            checkFavoriteViewState.observe(viewLifecycleOwner, {
                if (it) {
                    setAddedFavoritesItemState()
                }
            })
            addToFavoritesViewState.observe(viewLifecycleOwner, {
                if (it) {
                    Toast.makeText(context, R.string.item_added_to_favorites, Toast.LENGTH_LONG).show()
                    setAddedFavoritesItemState()
                } else {
                    Toast.makeText(context, R.string.item_added_to_favorites_failed, Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    private fun setAddedFavoritesItemState() {
        binding.fab.apply {
            setImageResource(R.drawable.ic_favorite_white_24dp)
            tag = ITEM_ALREADY_ADDED_TAG
        }
    }

    private fun setupFabButton() {
        viewModel.checkFavorite(name)
    }

    private fun setupListeners() {
        binding.fab.setOnClickListener {
            when (it.tag) {
                ITEM_OK_TAG -> {
                    addToFavorites()
                }
                ITEM_ALREADY_ADDED_TAG -> {
                    Toast.makeText(context, R.string.item_already_added, Toast.LENGTH_LONG).show()
                }
                else -> {
                    Toast.makeText(context, R.string.item_added_error_no_translation, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}