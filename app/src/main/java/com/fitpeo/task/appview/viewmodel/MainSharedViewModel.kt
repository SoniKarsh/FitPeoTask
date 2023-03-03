package com.fitpeo.task.appview.viewmodel

import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.fitpeo.task.appview.home.pagingfiles.data_source.PhotosRemoteDataSource
import com.fitpeo.task.model.ResFitpeoModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainSharedViewModel(private val photosApi: PhotosRemoteDataSource): ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    private val _errorPublisher = MutableStateFlow<Throwable?>(null)
    val errorPublisher = _errorPublisher.asStateFlow()
    val isLoading = _isLoading.asStateFlow()

    /*
    * Expose this method to your fragment or activity.
    **/
    fun getPhotos(): Flow<PagingData<ResFitpeoModel>> {
        return photosApi.getPhotos()
//            .map { pagingData ->
//                pagingData
//            }
//            .cachedIn(viewModelScope)
    }

    fun updateLoadingState(refresh: LoadState) {
        viewModelScope.launch {
            when (refresh) {
                is LoadState.Loading -> {
                    // Show loading spinner during initial load or refresh.
                    _isLoading.emit(true)
                }
                is LoadState.Error -> {
                    // Show the retry state if initial load or refresh fails.
                    _errorPublisher.emit(refresh.error)
                    _isLoading.emit(false)
                }
                is LoadState.NotLoading -> {
                    // Only shows the list if refresh succeeds.
                    _isLoading.emit(false)
                }
            }
        }
    }

}