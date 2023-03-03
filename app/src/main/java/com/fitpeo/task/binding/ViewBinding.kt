package com.fitpeo.task.binding

import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.fitpeo.task.R
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

object ViewBinding {

  @JvmStatic
  @BindingAdapter("srcImage")
  fun bindLoadImage(view: AppCompatImageView, url: String) {
    Picasso.get().load(url)
      .error(R.drawable.ic_placeholder)
      .noFade()
      .placeholder(R.drawable.ic_placeholder)
      .into(view)
  }

}
