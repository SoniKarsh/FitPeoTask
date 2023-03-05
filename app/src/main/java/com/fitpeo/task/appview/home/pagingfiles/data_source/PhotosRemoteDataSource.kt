package com.fitpeo.task.appview.home.pagingfiles.data_source

import androidx.paging.PagingData
import com.fitpeo.task.model.ResFitPeoModel
import kotlinx.coroutines.flow.Flow

interface PhotosRemoteDataSource {

    fun getPhotos(): Flow<PagingData<ResFitPeoModel>>

}