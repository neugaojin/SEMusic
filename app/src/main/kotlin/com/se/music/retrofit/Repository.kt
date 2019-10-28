package com.se.music.retrofit

import android.util.Log
import com.se.music.mvvm.SingerEntity
import com.se.music.online.model.ExpressInfoModel
import com.se.music.online.model.HallModel
import com.se.music.online.model.RecommendListModel
import com.se.senet.api.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 *Author: gaojin
 *Time: 2019-10-09 20:20
 */

object Repository {
    suspend fun getSinger(): SingerEntity {
        return withContext(Dispatchers.Main) {
            MusicRetrofit.INSTANCE.getSinger(100, 1).await()
        }
    }

    suspend fun getMusicHall(): HallModel {
        return withContext(Dispatchers.Main) {
            MusicRetrofit.INSTANCE.getMusicHall().await()
        }
    }

    suspend fun getRecommendList(): RecommendListModel {
        return withContext(Dispatchers.Main) {
            MusicRetrofit.INSTANCE.getRecommendList().await()
        }
    }

    suspend fun getNewSongInfo(): ExpressInfoModel {
        return withContext(Dispatchers.Main) {
            MusicRetrofit.INSTANCE.getNewSongInfo().await()
        }
    }
}