package com.fitpeo.task.appview.home.loader

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.fitpeo.task.R
import com.fitpeo.task.databinding.ItemPhotosLoadStateFooterBinding

class PhotoLoadStateViewHolder(
    private val binding: ItemPhotosLoadStateFooterBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnPhotosRetry.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.tvPhotosErrorDescription.text = loadState.error.localizedMessage
        }
        binding.progressPhotosLoadMore.isVisible = loadState is LoadState.Loading
        binding.btnPhotosRetry.isVisible = loadState is LoadState.Error
        binding.tvPhotosErrorDescription.isVisible = loadState is LoadState.Error
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