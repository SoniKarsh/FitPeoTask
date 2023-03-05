package com.fitpeo.task.appview.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitpeo.task.R
import com.fitpeo.task.appview.home.adapter.HomeListAdapter
import com.fitpeo.task.appview.home.loader.PhotoLoadStateAdapter
import com.fitpeo.task.appview.viewmodel.MainSharedViewModel
import com.fitpeo.task.databinding.FragmentHomeListBinding
import com.fitpeo.task.utils.AppLogger
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeListFragment : Fragment() {

    private lateinit var binding: FragmentHomeListBinding
    private val viewModel: MainSharedViewModel by activityViewModel()
    private var adapter: HomeListAdapter? = null
    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setBackArrow()
    }

    private fun setBackArrow() {
        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setDisplayShowHomeEnabled(false)
        }
    }

    private fun initView() {
        adapter = HomeListAdapter { image, pos, details ->
            var extras: FragmentNavigator.Extras? = null
            val actionNav = HomeListFragmentDirections.actionHomeListFragmentToDetailsFragment()
                .setDetails(details)
            details?.run {
                extras = FragmentNavigatorExtras(
                    image to id.toString()
                )
            }
            if (extras == null) {
                findNavController().navigate(
                    actionNav
                )
            } else {
                findNavController().navigate(
                    actionNav, extras!!
                )
            }
        }
        binding.rvPhotoList.let {
            gridLayoutManager = GridLayoutManager(
                requireContext(), 2, GridLayoutManager.VERTICAL, false
            )
            it.layoutManager = gridLayoutManager
            this@HomeListFragment.run {
                it.adapter = adapter?.withLoadStateHeaderAndFooter(
                    header = PhotoLoadStateAdapter { adapter?.retry() },
                    footer = PhotoLoadStateAdapter { adapter?.retry() }
                )
            }
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