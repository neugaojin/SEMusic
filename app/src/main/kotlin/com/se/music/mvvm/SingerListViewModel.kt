package com.se.music.mvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.se.music.support.retrofit.Repository

/**
 *Author: gaojin
 *Time: 2019-07-04 19:55
 */

class SingerListViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        const val DEFAULT_FILTER_INDEX = 0
    }

    private val initCategoryInfo = CategoryInfo(-100, "全部").apply {
        index = DEFAULT_FILTER_INDEX
    }

    val singerCategory = MutableLiveData<CategoryData?>()
    val areaFilterIndex = MutableLiveData<CategoryInfo>().apply {
        postValue(initCategoryInfo)
    }
    val sexFilterIndex = MutableLiveData<CategoryInfo>().apply {
        postValue(initCategoryInfo)
    }
    val genreFilterIndex = MutableLiveData<CategoryInfo>().apply {
        postValue(initCategoryInfo)
    }

    val flow =
            Pager(PagingConfig(pageSize = 80, initialLoadSize = 2))
            {
                SingerListPagingSource(areaFilterIndex, sexFilterIndex, genreFilterIndex)
            }.flow

    suspend fun fetchCategoryData() {
        singerCategory.value = Repository.getSingerCategory().data
    }
}