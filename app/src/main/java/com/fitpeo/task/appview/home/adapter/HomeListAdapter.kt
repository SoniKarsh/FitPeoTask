package com.fitpeo.task.appview.home.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fitpeo.task.R
import com.fitpeo.task.databinding.ItemPhotoFitpeoBinding
import com.fitpeo.task.model.ResFitpeoModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class HomeListAdapter(
    private val clickListener: (View, Int, ResFitpeoModel?) -> Unit
) : PagingDataAdapter<ResFitpeoModel, HomeListAdapter.HomeListViewHolder>(PhotosDiffCallback()) {

    private lateinit var bundle: Bundle

    companion object {
        // Loading ViewType
        const val LOADING_ITEM = 0

        // Photo ViewType
        const val PHOTO_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeListViewHolder {
        return HomeListViewHolder(
            ItemPhotoFitpeoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            clickListener
        )
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount) PHOTO_ITEM else LOADING_ITEM
    }

    override fun onBindViewHolder(holder: HomeListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HomeListViewHolder(
        val binding: ItemPhotoFitpeoBinding,
        private val clickListener: (View, Int, ResFitpeoModel?) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(fitPeoModel: ResFitpeoModel?) {
            binding.run {
                cpiProgress.visibility = View.VISIBLE
                root.setOnClickListener {
                    clickListener.invoke(image, bindingAdapterPosition, fitPeoModel)
                }
                fitPeoModel?.let { details ->
                    image.transitionName = details.id.toString()
                    name.text = details.title
                    Picasso.get().load(details.thumbnailUrl)
                        .error(R.drawable.ic_placeholder)
                        .into(image, object: Callback{
                            override fun onSuccess() {
                                cpiProgress.visibility = View.GONE
                            }
                            override fun onError(e: Exception?) {
                                cpiProgress.visibility = View.GONE
                            }
                        })
                }
            }
        }

    }
}