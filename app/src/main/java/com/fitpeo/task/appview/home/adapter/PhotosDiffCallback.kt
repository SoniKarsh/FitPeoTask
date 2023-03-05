package com.fitpeo.task.appview.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.fitpeo.task.model.ResFitPeoModel

class PhotosDiffCallback : DiffUtil.ItemCallback<ResFitPeoModel>() {
    override fun areItemsTheSame(oldItem: ResFitPeoModel, newItem: ResFitPeoModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ResFitPeoModel, newItem: ResFitPeoModel): Boolean {
        return oldItem == newItem
    }
}