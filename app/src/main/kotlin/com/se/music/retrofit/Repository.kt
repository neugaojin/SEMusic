package com.se.music.retrofit

import com.se.music.mvvm.SingerEntity
import com.se.senet.api.await
import kotlinx.coroutines.Dispatchers
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
}