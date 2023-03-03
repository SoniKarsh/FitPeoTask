package com.fitpeo.task.utils.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.*

/**
 * Extension method to show toast for Context.
 */
fun Context?.toast(text: CharSequence, duration: Int = Toast.LENGTH_LONG) =
    this?.let { Toast.makeText(it, text, duration).show() }

/**
 * Extension method to show toast for Context.
 */
fun Context?.toast(@StringRes textId: Int, duration: Int = Toast.LENGTH_LONG) =
    this?.let { Toast.makeText(it, textId, duration).show() }
