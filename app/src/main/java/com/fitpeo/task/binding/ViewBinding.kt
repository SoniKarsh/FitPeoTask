package com.fitpeo.task.binding

import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.fitpeo.task.R
import com.fitpeo.task.model.ResFitPeoModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

object ViewBinding {

    @JvmStatic
    @BindingAdapter("srcImage", "progressBar")
    fun bindLoadImage(view: AppCompatImageView, details: ResFitPeoModel, pb: ProgressBar) {
        pb.visibility = View.VISIBLE
        Picasso.get().load(details.url)
            .error(R.drawable.ic_placeholder)
            .noFade()
            .into(view, object : Callback {
            override fun onSuccess() {
                pb.visibility = View.GONE
            }
            override fun onError(e: Exception?) {
                pb.visibility = View.GONE
            }
        })
    }

}
