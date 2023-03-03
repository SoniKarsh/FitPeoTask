package com.fitpeo.task.appview.home.pagingfiles.data_source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fitpeo.task.api.interfaces.FitPeoPhotosApi
import com.fitpeo.task.appview.home.pagingfiles.PhotosPagingSource
import com.fitpeo.task.model.ResFitpeoModel
import kotlinx.coroutines.flow.Flow

const val NETWORK_PAGE_SIZE = 50

class PhotosRemoteDataSourceImpl(private val photosApi: FitPeoPhotosApi): PhotosRemoteDataSource {
    override fun getPhotos(): Flow<PagingData<ResFitpeoModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = NETWORK_PAGE_SIZE
            ),
            pagingSourceFactory = {
                PhotosPagingSource(photosApi)
            }
        ).flow
    }
}