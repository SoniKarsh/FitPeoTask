package com.fitpeo.task.appview.home.pagingfiles

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fitpeo.task.api.interfaces.FitPeoPhotosApi
import com.fitpeo.task.appview.home.pagingfiles.data_source.NETWORK_PAGE_SIZE
import com.fitpeo.task.model.ResFitPeoModel
import retrofit2.HttpException
import java.io.IOException

class PhotosPagingSource(private val fitPeoPhotosApi: FitPeoPhotosApi): PagingSource<Int, ResFitPeoModel>() {

    override val jumpingSupported: Boolean
        get() = super.jumpingSupported
    override val keyReuseSupported: Boolean
        get() = super.keyReuseSupported

    /**
     * The refresh key is used for subsequent calls to PagingSource.Load after the initial load.
     */
    override fun getRefreshKey(state: PagingState<Int, ResFitPeoModel>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResFitPeoModel> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = fitPeoPhotosApi.getPhotos(
                pageIndex
            )
            val nextKey =
                if (response.isEmpty()) {
                    null
                } else {
                    // By default, initial load size = 3 * NETWORK PAGE SIZE
                    // ensure we're not requesting duplicating items at the 2nd request
                    pageIndex + (params.loadSize / NETWORK_PAGE_SIZE)
                }
            LoadResult.Page(
                data = response,
                prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else (pageIndex - (params.loadSize / NETWORK_PAGE_SIZE)),
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}