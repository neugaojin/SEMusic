package com.se.music.scene.hall

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.se.music.online.event.ScrollEvent
import com.se.music.online.model.ExpressInfoModel
import com.se.music.online.model.HallModel
import com.se.music.online.model.RecommendListModel
import com.se.music.support.retrofit.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *Author: gaojin
 *Time: 2019-10-21 18:27
 */

class HallViewModel(application: Application) : AndroidViewModel(application) {

    val notification: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val hall: MutableLiveData<HallModel?> by lazy {
        MutableLiveData<HallModel?>()
    }

    val recommendList: MutableLiveData<RecommendListModel?> by lazy {
        MutableLiveData<RecommendListModel?>()
    }

    val expressInfo: MutableLiveData<ExpressInfoModel?> by lazy {
        MutableLiveData<ExpressInfoModel?>()
    }

    val scrollEvent: MutableLiveData<ScrollEvent> by lazy {
        MutableLiveData<ScrollEvent>()
    }

    fun fetchData() {
        viewModelScope.launch {
            getFirstData()
        }
    }

    private suspend fun getFirstData() {
        return withContext(Dispatchers.Main) {
            val hallResult = async { Repository.getMusicHall().body() }
            val recommendListResult = async { Repository.getRecommendList().body() }
            val expressInfoResult = async { Repository.getNewSongInfo().body() }
            //execute by order
            hall.value = hallResult.await()
            recommendList.value = recommendListResult.await()
            expressInfo.value = expressInfoResult.await()
            notification.value = true
        }
    }
}