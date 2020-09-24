package com.se.music.uamp

import android.content.Context
import com.se.music.uamp.viewmodel.MainViewModel
import com.se.music.uamp.viewmodel.SongListViewModel

/**
 *Author: gaojin
 *Time: 2020/4/21 2:18 PM
 */

object InjectUtils {

    fun provideMainViewModel(context: Context) = MainViewModel.Factory(MusicServiceConnection.getInstance())

    fun provideSongListViewModel(context: Context) = SongListViewModel.Factory(MusicServiceConnection.getInstance())
}