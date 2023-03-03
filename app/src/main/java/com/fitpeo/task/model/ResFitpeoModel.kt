package com.fitpeo.task.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResFitpeoModel(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String,
): Parcelable