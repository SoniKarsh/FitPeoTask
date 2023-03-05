package com.fitpeo.task.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResFitPeoModel(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String,
): Parcelable