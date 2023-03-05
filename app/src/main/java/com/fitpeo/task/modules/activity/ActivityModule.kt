package com.fitpeo.task.modules.activity

import com.fitpeo.task.api.interfaces.FitPeoPhotosApi
import com.fitpeo.task.appview.home.pagingfiles.data_source.PhotosRemoteDataSource
import com.fitpeo.task.appview.home.pagingfiles.data_source.PhotosRemoteDataSourceImpl
import com.fitpeo.task.appview.viewmodel.MainSharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val activityModule = module {
    viewModel {
        MainSharedViewModel(
            get()
        )
    }

    single {
        providePhotoRemoteDS(get())
    }

}

fun providePhotoRemoteDS(photosApi: FitPeoPhotosApi): PhotosRemoteDataSource {
    return PhotosRemoteDataSourceImpl(photosApi)
}
