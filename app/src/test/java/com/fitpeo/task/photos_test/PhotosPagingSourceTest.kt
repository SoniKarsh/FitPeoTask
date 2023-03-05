package com.fitpeo.task.photos_test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.fitpeo.task.api.interfaces.FitPeoPhotosApi
import com.fitpeo.task.appview.home.pagingfiles.PhotosPagingSource
import com.fitpeo.task.model.ResFitPeoModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.given

@ExperimentalCoroutinesApi
class PhotosPagingSourceTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var api: FitPeoPhotosApi

    lateinit var photosPagingSource: PhotosPagingSource

    companion object {
        val photosResponse = arrayListOf(
            ResFitPeoModel(
                albumId = 1,
                id = 1,
                title = "accusamus beatae ad facilis cum similique qui sunt",
                thumbnailUrl = "https://via.placeholder.com/150/92c952",
                url = "https://via.placeholder.com/600/92c952"
            )
        )

        const val PAGE_SIZE = 50
        const val ERROR_404 = "404"

    }

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        photosPagingSource = PhotosPagingSource(api)
    }

    @Test
    fun `photos paging source load - failure - http error`() = runTest {
        val error = RuntimeException(ERROR_404, Throwable())
        given(api.getPhotos(any())).willThrow(error)
        val expectedResult = PagingSource.LoadResult.Error<Int, ResFitPeoModel>(error)
        Assert.assertEquals(
            expectedResult, photosPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 1,
                    loadSize = PAGE_SIZE,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `photos paging source load - failure - received null`() = runTest {
        `when`(api.getPhotos(any())).thenReturn(null)
        val expectedResult =
            PagingSource.LoadResult.Error<Int, ResFitPeoModel>(NullPointerException())
        Assert.assertEquals(
            expectedResult.toString(), photosPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 1,
                    loadSize = PAGE_SIZE,
                    placeholdersEnabled = false
                )
            ).toString()
        )
    }

    /** The expected result should be the photosResponse + nextKey value should be 2 and prevKey value should be null
    as it’s just the first page is loaded */
    @Test
    fun `photos paging source refresh - success`() = runTest {
        `when`(api.getPhotos(any())).thenReturn(
            photosResponse
        )

        val expectedResult = PagingSource.LoadResult.Page(
            data = photosResponse,
            prevKey = null,
            nextKey = 2
        )
        Assert.assertEquals(
            expectedResult, photosPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 1,
                    loadSize = PAGE_SIZE,
                    placeholdersEnabled = false
                )
            )
        )
    }

    /** The expected result should be the photosResponse + nextKey value should be 3
     * and prevKey value should be 1 as it’s the 2nd page is loaded*/
    @Test
    fun `photos paging source append - success`() = runTest {
        `when`(api.getPhotos(any())).thenReturn(
            photosResponse
        )
        val expectedResult = PagingSource.LoadResult.Page(
            data = photosResponse,
            prevKey = 1,
            nextKey = 3
        )
        Assert.assertEquals(
            expectedResult, photosPagingSource.load(
                PagingSource.LoadParams.Append(
                    key = 2,
                    loadSize = PAGE_SIZE,
                    placeholdersEnabled = false
                )
            )
        )
    }

    /** The expected result should be the photosResponse + nextKey value should be 2
     * and prevKey value should be null it’s just the first page is loaded again*/
    @Test
    fun `photos paging source prepend - success`() = runTest {
        `when`(api.getPhotos(any())).thenReturn(
            photosResponse
        )
        val expectedResult = PagingSource.LoadResult.Page(
            data = photosResponse,
            prevKey = null,
            nextKey = 2
        )
        Assert.assertEquals(
            expectedResult, photosPagingSource.load(
                PagingSource.LoadParams.Prepend(
                    key = 1,
                    loadSize = PAGE_SIZE,
                    placeholdersEnabled = false
                )
            )
        )
    }
}