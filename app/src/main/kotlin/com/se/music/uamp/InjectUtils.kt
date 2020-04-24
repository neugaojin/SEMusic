package com.se.music.uamp

import android.content.ComponentName
import android.content.Context
import com.se.service.SeMusicService

/**
 *Author: gaojin
 *Time: 2020/4/21 2:18 PM
 */

object InjectUtils {
    private fun getMSC(context: Context) = MusicServiceConnection.getInstance(context, ComponentName(context, SeMusicService::class.java))

    fun provideMainActivityViewModel(context: Context) = MainActivityViewModel.Factory(getMSC(context.applicationContext))

    fun provideSceneViewModel(context: Context) = LocalSongViewModel.Factory(getMSC(context.applicationContext))
}