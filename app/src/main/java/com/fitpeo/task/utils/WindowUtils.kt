package com.fitpeo.task.utils

import android.graphics.Insets
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity

object WindowUtils {

    @Suppress("DEPRECATION")
    fun getWindowHeight(activity: AppCompatActivity): Int {
        activity.let { context ->
            return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                val displayMetrics = DisplayMetrics()
                context.windowManager.defaultDisplay.getMetrics(displayMetrics)
                displayMetrics.heightPixels
            } else {
                val windowMetrics = context.windowManager?.currentWindowMetrics
                windowMetrics?.let {
                    val insets: Insets = it.windowInsets
                        .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
                    windowMetrics.bounds.height() - insets.top - insets.bottom
                } ?: let {
                    0
                }
            }
        }
    }

    fun getWindowWidth(activity: AppCompatActivity): Int {
        activity.let { context ->
            return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                val displayMetrics = DisplayMetrics()
                context.windowManager.defaultDisplay.getMetrics(displayMetrics)
                displayMetrics.widthPixels
            } else {
                val windowMetrics = context.windowManager?.currentWindowMetrics
                windowMetrics?.let {
                    val insets: Insets = it.windowInsets
                        .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
                    windowMetrics.bounds.width()
                } ?: let {
                    0
                }
            }
        }
    }

}