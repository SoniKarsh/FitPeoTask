package com.fitpeo.task.appview.home.loader

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

/*
 * Created by Christopher Elias on 10/05/2021
 * christopher.elias@loop-ideas.com
 *
 * Loop Ideas
 * Lima, Peru.
 */

class PhotoLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<PhotoLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: PhotoLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PhotoLoadStateViewHolder {
        return PhotoLoadStateViewHolder.create(parent, retry)
    }
}