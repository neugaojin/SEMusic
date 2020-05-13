package com.se.music.uamp

import android.content.ComponentName
import android.content.Context
import com.se.music.uamp.viewmodel.SongListViewModel
import com.se.music.uamp.viewmodel.MainViewModel
import com.se.service.SeMusicService

/**
 *Author: gaojin
 *Time: 2020/4/21 2:18 PM
 */

object InjectUtils {
    fun getMSC(context: Context) = MusicServiceConnection.getInstance(context, ComponentName(context, SeMusicService::class.java))

    fun provideMainViewModel(context: Context) = MainViewModel.Factory(getMSC(context.applicationContext))

    fun provideSongListViewModel(context: Context) = SongListViewModel.Factory(getMSC(context.applicationContext))
}