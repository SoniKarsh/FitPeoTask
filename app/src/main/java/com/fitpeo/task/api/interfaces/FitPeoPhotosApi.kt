package com.fitpeo.task.api.interfaces

import com.fitpeo.task.api.ApiConstants
import com.fitpeo.task.model.ResFitPeoModel
import retrofit2.http.GET
import retrofit2.http.Query

interface FitPeoPhotosApi {

    @GET(ApiConstants.PHOTOS)
    suspend fun getPhotos(@Query("albumId") albumId: Int): ArrayList<ResFitPeoModel>

}