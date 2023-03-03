package com.fitpeo.task.modules.activity

import com.fitpeo.task.api.ApiConstants
import com.fitpeo.task.api.interfaces.FitPeoPhotosApi
import com.fitpeo.task.appview.home.pagingfiles.data_source.PhotosRemoteDataSource
import com.fitpeo.task.appview.home.pagingfiles.data_source.PhotosRemoteDataSourceImpl
import com.fitpeo.task.appview.viewmodel.MainSharedViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
