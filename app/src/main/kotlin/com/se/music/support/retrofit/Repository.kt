package com.se.music.support.retrofit

import com.se.music.mvvm.SingerEntity
import com.se.music.online.model.ExpressInfoModel
import com.se.music.online.model.HallModel
import com.se.music.online.model.RecommendListModel
import com.se.senet.api.await

/**
 *Author: gaojin
 *Time: 2019-10-09 20:20
 */

object Repository {
    suspend fun getSinger(): SingerEntity {
        return MusicRetrofit.INSTANCE.getSinger(100, 1).await()
    }

    suspend fun getMusicHall(): HallModel? {
        return try {
            MusicRetrofit.INSTANCE.getMusicHall().await()
        } catch (e: Throwable) {
            e.printStackTrace()
            null
        }
    }

    suspend fun getRecommendList(): RecommendListModel? {
        return try {
            MusicRetrofit.INSTANCE.getRecommendList().await()
        } catch (e: Throwable) {
            e.printStackTrace()
            null
        }
    }

    suspend fun getNewSongInfo(): ExpressInfoModel? {
        return try {
            MusicRetrofit.INSTANCE.getNewSongInfo().await()
        } catch (e: Throwable) {
            e.printStackTrace()
            null
        }
    }
}