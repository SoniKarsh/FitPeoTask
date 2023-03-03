package com.fitpeo.task.appview.home.loader

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.fitpeo.task.R
import com.fitpeo.task.databinding.ItemPhotosLoadStateFooterBinding

/*
 * Created by Christopher Elias on 10/05/2021
 * christopher.elias@loop-ideas.com
 *
 * Loop Ideas
 * Lima, Peru.
 */

class PhotoLoadStateViewHolder(
    private val binding: ItemPhotosLoadStateFooterBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnMoviesRetry.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.tvMoviesErrorDescription.text = loadState.error.localizedMessage
        }
        binding.progressMoviesLoadMore.isVisible = loadState is LoadState.Loading
        binding.btnMoviesRetry.isVisible = loadState is LoadState.Error
        binding.tvMoviesErrorDescription.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): PhotoLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_photos_load_state_footer, parent, false)
            val binding = ItemPhotosLoadStateFooterBinding.bind(view)
            return PhotoLoadStateViewHolder(binding, retry)
        }
    }
}