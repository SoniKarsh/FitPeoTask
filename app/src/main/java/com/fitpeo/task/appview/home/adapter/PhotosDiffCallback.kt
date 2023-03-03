package com.fitpeo.task.appview.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.fitpeo.task.model.ResFitpeoModel

class PhotosDiffCallback : DiffUtil.ItemCallback<ResFitpeoModel>() {
    override fun areItemsTheSame(oldItem: ResFitpeoModel, newItem: ResFitpeoModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ResFitpeoModel, newItem: ResFitpeoModel): Boolean {
        return oldItem == newItem
    }
}