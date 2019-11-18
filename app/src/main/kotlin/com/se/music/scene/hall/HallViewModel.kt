package com.se.music.scene.hall

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.se.music.online.event.ScrollEvent
import com.se.music.online.model.ExpressInfoModel
import com.se.music.online.model.HallModel
import com.se.music.online.model.RecommendListModel
import com.se.music.retrofit.Repository
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 *Author: gaojin
 *Time: 2019-10-21 18:27
 */

class HallViewModel(application: Application) : AndroidViewModel(application) {

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

    init {
        viewModelScope.launch {
            val time = measureTimeMillis {
                val hallResult = async { Repository.getMusicHall() }
                val recommendListResult = async { Repository.getRecommendList() }
                val expressInfoResult = async { Repository.getNewSongInfo() }
                hall.value = hallResult.await()
                recommendList.value = recommendListResult.await()
                expressInfo.value = expressInfoResult.await()
            }
            Log.e("HallViewModel", "$time")
        }
    }
}