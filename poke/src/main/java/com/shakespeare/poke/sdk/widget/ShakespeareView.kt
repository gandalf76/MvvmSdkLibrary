package com.shakespeare.poke.sdk.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.shakespeare.poke.R
import com.shakespeare.poke.databinding.ShakespeareviewBinding

class ShakespeareView @JvmOverloads constructor(
                      context: Context,
                      attrs: AttributeSet? = null,
                      defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr)  {

    private var binding: ShakespeareviewBinding
    private lateinit var description: String
    init {
        val layoutInflater = LayoutInflater.from(context)
        binding = ShakespeareviewBinding.inflate(layoutInflater, this, true)

        attrs?.let {
            val styledAttributes = context.obtainStyledAttributes(it, R.styleable.ShakespeareView, 0, 0)

            description = styledAttributes.getString(R.styleable.ShakespeareView_description).toString()
            val imageUrl = styledAttributes.getString(R.styleable.ShakespeareView_imageUrl)
            val showLoading = styledAttributes.getBoolean(R.styleable.ShakespeareView_showLoading, false)

            showLoading(showLoading)
            setDescription(description)
            setImage(imageUrl)

            styledAttributes.recycle()
        }
    }

    fun showLoading(show: Boolean) {
        when (show) {
            true -> {
                binding.progress.visibility = View.VISIBLE
                binding.avatar.visibility = View.GONE
                binding.description.visibility = View.GONE
            } else -> {
                binding.progress.visibility = View.GONE
                binding.avatar.visibility = View.VISIBLE
                binding.description.visibility = View.VISIBLE
            }
        }
    }

    fun setDescription(description: String?) {
        binding.description.text = description
    }

    fun setImage(url: String?) {
        Glide
            .with(context)
            .load(url).error(R.drawable.ic_baseline_error_24)
            .centerCrop()
            .into(binding.avatar)
    }

}