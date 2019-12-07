package com.se.music.init

import com.se.music.retrofit.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull

/**
 *Author: gaojin
 *Time: 2019-12-06 18:02
 */

object InitData {
    suspend fun init() {
        withContext(Dispatchers.Main) {
            val hallResult = async { Repository.getMusicHall() }
            val recommendListResult = async { Repository.getRecommendList() }
            val expressInfoResult = async { Repository.getNewSongInfo() }

            GlobalFirstData.ghHall = withTimeoutOrNull(Welcome.WEL_MAX_TIME) {
                hallResult.await()
            }

            GlobalFirstData.gfRecommendData = withTimeoutOrNull(Welcome.WEL_MAX_TIME) {
                recommendListResult.await()
            }

            GlobalFirstData.gfExpressInfo = withTimeoutOrNull(Welcome.WEL_MAX_TIME) {
                expressInfoResult.await()
            }
        }
    }
}