package com.fitpeo.task

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.fitpeo.task.appview.details.DetailsActivity
import com.fitpeo.task.appview.home.adapter.HomeListAdapter
import com.fitpeo.task.appview.home.loader.PhotoLoadStateAdapter
import com.fitpeo.task.appview.viewmodel.MainSharedViewModel
import com.fitpeo.task.base.BaseActivity
import com.fitpeo.task.databinding.ActivityMainBinding
import com.fitpeo.task.model.ResFitPeoModel
import com.fitpeo.task.utils.AppConstants
import com.fitpeo.task.utils.extensions.toast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainSharedViewModel by viewModel()
    private var adapter: HomeListAdapter? = null
    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

        setErrorListener()

        setSupportActionBar(binding.toolbar)

        setBackArrow()

    }

    private fun setBackArrow() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
    }

    private fun setErrorListener() {
        lifecycleScope.launch {
            viewModel.errorPublisher.collectLatest {
                if (it != null)
                    this@MainActivity.toast(it.message.toString())
            }
        }
    }

    private fun intentDetails(image: View, pos: Int, details: ResFitPeoModel?) {
        var options: ActivityOptionsCompat?
        startActivity(
            Intent(this, DetailsActivity::class.java).apply {
                options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@MainActivity,
                    image,
                    details?.id.toString()
                )
                putExtra(AppConstants.DETAILS, details)
            }, options?.toBundle()
        )
    }

    private fun initViews() {
        setSupportActionBar(binding.toolbar)
        adapter = HomeListAdapter { image, pos, details ->
            intentDetails(image, pos, details)
        }
        binding.rvPhotoList.let {
            gridLayoutManager = GridLayoutManager(
                this, 2, GridLayoutManager.VERTICAL, false
            )
            it.layoutManager = gridLayoutManager
            it.adapter = adapter?.withLoadStateHeaderAndFooter(
                header = PhotoLoadStateAdapter { adapter?.retry() },
                footer = PhotoLoadStateAdapter { adapter?.retry() }
            )
        }

        loadData()

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (adapter?.getItemViewType(position) == HomeListAdapter.LOADING_ITEM) {
                    1
                } else {
                    2
                }
            }
        }

        adapter?.addLoadStateListener { loadState ->
            viewModel.updateLoadingState(loadState.source.refresh)
            renderUi(loadState)
        }

        clickListeners()

    }

    private fun clickListeners() {
        binding.btnRetry.setOnClickListener { adapter?.retry() }
    }

    private fun renderUi(loadState: CombinedLoadStates) {
        val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter?.itemCount == 0

        binding.rvPhotoList.isVisible = !isListEmpty
        binding.tvPhotosEmpty.isVisible = isListEmpty

        // Only shows the list if refresh succeeds.
        binding.rvPhotoList.isVisible = loadState.source.refresh is LoadState.NotLoading
        // Show loading spinner during initial load or refresh.
        binding.progressBarPhotos.isVisible = loadState.source.refresh is LoadState.Loading
        // Show the retry state if initial load or refresh fails.
        binding.btnRetry.isVisible = loadState.source.refresh is LoadState.Error
    }

    private fun loadData() {
        lifecycleScope.launch {
            viewModel.resultPublisher.collectLatest {
                it?.run {
                    adapter?.submitData(this)
                }
            }
        }
    }

}