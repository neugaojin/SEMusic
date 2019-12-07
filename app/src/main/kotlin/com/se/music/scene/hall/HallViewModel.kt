package com.se.music.scene.hall

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.se.music.base.log.Loger
import com.se.music.init.GlobalFirstData
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
            getFirstData()
            Loger.e("HallViewModel") { "notification" }
        }
    }

    private suspend fun getFirstData() {
        return withContext(Dispatchers.Main) {
            val hallResult = async { Repository.getMusicHall() }
            val recommendListResult = async { Repository.getRecommendList() }
            val expressInfoResult = async { Repository.getNewSongInfo() }

            val result1 = if (GlobalFirstData.ghHall == null) {
                hallResult.await()
            } else {
                GlobalFirstData.ghHall
            }
            hall.value = result1


            val result2 = if (GlobalFirstData.gfRecommendData == null) {
                recommendListResult.await()
            } else {
                GlobalFirstData.gfRecommendData
            }
            recommendList.value = result2

            val result3 = if (GlobalFirstData.gfExpressInfo == null) {
                expressInfoResult.await()
            } else {
                GlobalFirstData.gfExpressInfo
            }
            expressInfo.value = result3
        }
    }
}