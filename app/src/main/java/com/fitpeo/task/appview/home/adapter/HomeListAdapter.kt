package com.fitpeo.task.appview.home.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fitpeo.task.R
import com.fitpeo.task.appview.home.HomeListFragmentDirections
import com.fitpeo.task.databinding.ItemPhotoFitpeoBinding
import com.fitpeo.task.model.ResFitpeoModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class HomeListAdapter : PagingDataAdapter<ResFitpeoModel, HomeListAdapter.HomeListViewHolder>(PhotosDiffCallback()) {

    private lateinit var bundle: Bundle

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeListViewHolder {
        val holder = HomeListViewHolder(
            ItemPhotoFitpeoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
        holder.binding.root.setOnClickListener { view ->
            getItem(holder.bindingAdapterPosition)?.let { item ->
                view.findNavController().navigate(
                    HomeListFragmentDirections.actionHomeListFragmentToDetailsFragment()
                        .setDetails(item)
                )
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: HomeListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HomeListViewHolder(
        val binding: ItemPhotoFitpeoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(fitPeoModel: ResFitpeoModel?) {
            fitPeoModel?.run {
                binding.name.text = title
                Picasso.get().load(thumbnailUrl)
                    .error(R.drawable.ic_placeholder)
                    .noFade()
                    .placeholder(R.drawable.ic_placeholder)
                    .into(binding.image, object: Callback{
                        override fun onSuccess() {
                            binding.cpiProgress.visibility = View.GONE
                        }
                        override fun onError(e: Exception?) {
                            binding.cpiProgress.visibility = View.GONE
                        }
                    })
            }
        }

    }
}