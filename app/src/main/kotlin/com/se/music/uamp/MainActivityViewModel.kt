package com.se.music.uamp

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 *Author: gaojin
 *Time: 2020/4/16 7:19 PM
 */

class MainActivityViewModel(private val musicServiceConnection: MusicServiceConnection) : ViewModel() {
    val rootMediaId: LiveData<String> = Transformations.map(musicServiceConnection.isConnected) { isConnected ->
        if (isConnected) {
            musicServiceConnection.rootMediaId
        } else {
            null
        }
    }

    class Factory(private val musicServiceConnection: MusicServiceConnection) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainActivityViewModel(musicServiceConnection) as T
        }
    }
}