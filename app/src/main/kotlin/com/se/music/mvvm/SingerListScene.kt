package com.se.music.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.bytedance.scene.ktx.viewModels
import com.google.android.material.appbar.AppBarLayout
import com.se.music.R
import com.se.music.base.scene.baseContext
import com.se.music.scene.base.SeCompatScene
import com.se.music.widget.loading.LoadingView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 *Author: gaojin
 *Time: 2020/10/6 7:55 PM
 */

class SingerListScene : SeCompatScene() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var areaRecyclerView: RecyclerView
    private lateinit var sexRecyclerView: RecyclerView
    private lateinit var genreRecyclerView: RecyclerView
    private lateinit var appBarLayout: AppBarLayout
    private lateinit var loadingView: LoadingView

    private val viewModel: SingerListViewModel by viewModels()

    private val pagingAdapter = SingerAdapter()

    override fun createContentView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {
        return LayoutInflater.from(baseContext()).inflate(R.layout.scene_singer_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(getString(R.string.classify_singer))
        recyclerView = requireViewById(R.id.root_recycler_view)
        areaRecyclerView = requireViewById(R.id.area_recycler_view)
        sexRecyclerView = requireViewById(R.id.sex_recycler_view)
        genreRecyclerView = requireViewById(R.id.genre_recycler_view)
        appBarLayout = requireViewById(R.id.app_bar_layout)
        loadingView = requireViewById(R.id.loading_view)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = pagingAdapter

            itemAnimator?.let {
                it.addDuration = 0
                it.changeDuration = 0
                it.moveDuration = 0
                it.removeDuration = 0
                (it as SimpleItemAnimator).supportsChangeAnimations = false
            }
        }

        setFilterRecyclerView(areaRecyclerView)
        setFilterRecyclerView(sexRecyclerView)
        setFilterRecyclerView(genreRecyclerView)

        (areaRecyclerView.adapter as SingerCategoryAdapter).setOnItemClickListener { categoryInfo ->
            viewModel.areaFilterIndex.value = categoryInfo
        }
        (sexRecyclerView.adapter as SingerCategoryAdapter).setOnItemClickListener { categoryInfo ->
            viewModel.sexFilterIndex.value = categoryInfo
        }
        (genreRecyclerView.adapter as SingerCategoryAdapter).setOnItemClickListener { categoryInfo ->
            viewModel.genreFilterIndex.value = categoryInfo
        }
        coroutineScope.launch {
            viewModel.fetchCategoryData()
        }
        coroutineScope.launch {
            pagingAdapter.loadStateFlow.collectLatest { loadState ->
                loadingView.visibility = when (loadState.refresh) {
                    is LoadState.NotLoading -> {
                        recyclerView.scrollToPosition(0)
                        View.GONE
                    }
                    is LoadState.Loading -> {
                        View.VISIBLE
                    }
                    is LoadState.Error -> {
                        recyclerView.scrollToPosition(0)
                        View.GONE
                    }
                }
            }
        }
        coroutineScope.launch {
            viewModel.flow.collectLatest {
                pagingAdapter.submitData(it)
            }
        }

        viewModel.apply {
            val owner = this@SingerListScene
            singerCategory.observe(owner, Observer {
                it?.let { categoryData ->
                    getAdapter(areaRecyclerView).setData(categoryData.area)
                    getAdapter(sexRecyclerView).setData(categoryData.sex)
                    getAdapter(genreRecyclerView).setData(categoryData.genre)
                }
            })

            areaFilterIndex.observe(owner, {
                getAdapter(areaRecyclerView).updateSelectPosition(it.index)
                refreshData()
            })

            sexFilterIndex.observe(owner, {
                getAdapter(sexRecyclerView).updateSelectPosition(it.index)
                refreshData()
            })

            genreFilterIndex.observe(owner, {
                getAdapter(genreRecyclerView).updateSelectPosition(it.index)
                refreshData()
            })
        }
    }

    private fun setFilterRecyclerView(recyclerView: RecyclerView) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = SingerCategoryAdapter()
        }
    }

    private fun getAdapter(recyclerView: RecyclerView): SingerCategoryAdapter {
        if (recyclerView.adapter is SingerCategoryAdapter) {
            return recyclerView.adapter as SingerCategoryAdapter
        } else {
            throw IllegalArgumentException("the adapter of recyclerView is not SingerCategoryAdapter")
        }
    }

    private fun resetLoadingHeight() {
        loadingView.layoutParams = (loadingView.layoutParams as CoordinatorLayout.LayoutParams).apply {
            topMargin = appBarLayout.measuredHeight
        }
    }

    private fun refreshData() {
        resetLoadingHeight()
        pagingAdapter.refresh()
    }
}