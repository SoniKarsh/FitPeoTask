package com.fitpeo.task.binding

import android.R.attr.y
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.drawable.toDrawable
import androidx.databinding.BindingAdapter
import com.fitpeo.task.R
import com.fitpeo.task.model.ResFitpeoModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


object ViewBinding {

    @JvmStatic
    @BindingAdapter("srcImage", "progressBar")
    fun bindLoadImage(view: AppCompatImageView, details: ResFitpeoModel, pb: ProgressBar) {
        pb.visibility = View.VISIBLE
        Picasso.get().load(details.url)
            .error(R.drawable.ic_placeholder)
            .noFade()
            .into(view, object : Callback {
            override fun onSuccess() {
                pb.visibility = View.GONE
//                loadImages(view, details, null)
            }
            override fun onError(e: Exception?) {
                pb.visibility = View.GONE
            }
        })
    }

    private fun loadImages(view: AppCompatImageView, details: ResFitpeoModel, callback: Callback?) {
        if (callback != null) {
            Picasso.get().load(details.thumbnailUrl)
                .error(R.drawable.ic_placeholder)
                .into(view, callback)
        } else {
            Picasso.get().load(details.url)
                .error(R.drawable.ic_placeholder)
                .into(view)
        }
    }

}
