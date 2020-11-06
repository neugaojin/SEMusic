package com.se.music.mvvm

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.se.music.support.retrofit.Repository
import retrofit2.HttpException
import java.io.IOException

/**
 *Author: gaojin
 *Time: 2020/10/7 11:07 PM
 */

class SingerListPagingSource(
    private val area: LiveData<CategoryInfo>,
    private val sex: LiveData<CategoryInfo>,
    private val genre: LiveData<CategoryInfo>,
    private val resultCache: MutableMap<String, LoadResult<Int, Singer>>
) : PagingSource<Int, Singer>() {
    companion object {
        private const val ALL = -100
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Singer> {
        return try {
            var nextPageNumber = params.key ?: 1
            val key = getKey(nextPageNumber)
            return resultCache.getOrElse(key, {
                val result = Repository.getSinger(
                    params.loadSize,
                    nextPageNumber,
                    getCategoryId(area),
                    getCategoryId(sex),
                    getCategoryId(genre)
                )
                val data = result.data?.list ?: emptyList()
                return@getOrElse if (data.isNotEmpty()) {
                    nextPageNumber++
                    LoadResult.Page(
                        data = data,
                        prevKey = null,
                        nextKey = nextPageNumber
                    ).also {
                        resultCache[key] = it
                    }
                } else {
                    LoadResult.Page(
                        data = data,
                        prevKey = null,
                        nextKey = null
                    )
                }
            })
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    private fun getCategoryId(liveData: LiveData<CategoryInfo>) = liveData.value?.id ?: ALL

    private fun getKey(pageNumber: Int) = "${pageNumber}_${area.value?.name}_${sex.value?.name}_${genre.value?.name}"
}