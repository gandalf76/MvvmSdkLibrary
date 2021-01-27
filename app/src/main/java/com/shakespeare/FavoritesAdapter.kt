package com.shakespeare

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class FavoritesAdapter(private val favorites: MutableList<String> = mutableListOf(), private val listener: OnFavoriteClickListener?) :
    RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val containerView: ConstraintLayout = view.findViewById(R.id.container)
        val favoriteTextView: TextView = view.findViewById(R.id.favorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_item, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        setupFavoriteItem(position, holder)
    }

    override fun getItemCount() = favorites.size

    private fun setupFavoriteItem(position: Int, holder: FavoriteViewHolder) {
        val favorite = favorites[position]
        holder.favoriteTextView.text = favorite
        setupClick(holder, favorite)
    }

    private fun setupClick(holder: FavoriteViewHolder, favorite: String) {
        holder.containerView.setOnClickListener {
                listener?.onItemClicked(favorite)
        }
    }

    fun updateItem(favorites: List<String>) {
        this.favorites.clear()
        this.favorites.addAll(favorites)
        notifyDataSetChanged()
    }

    interface OnFavoriteClickListener {

        fun onItemClicked(name: String)
    }
}