package com.se.music.uamp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.se.music.base.log.Loger
import com.se.service.extensions.id
import com.se.service.extensions.isPlayEnabled
import com.se.service.extensions.isPlaying
import com.se.service.extensions.isPrepared

/**
 *Author: gaojin
 *Time: 2020/4/16 7:19 PM
 */

class MainActivityViewModel(private val musicServiceConnection: MusicServiceConnection) : ViewModel() {

    fun playMedia(mediaItem: MediaItemData, pauseAllowed: Boolean = true) {
        Loger.e { "playMedia: $mediaItem" }
        val nowPlaying = musicServiceConnection.nowPlaying.value
        val transportControls = musicServiceConnection.transportControls

        val isPrepared = musicServiceConnection.playbackState.value?.isPrepared ?: false

        if (isPrepared && mediaItem.mediaId == nowPlaying?.id) {
            musicServiceConnection.playbackState.value?.let { playbackState ->
                when {
                    playbackState.isPlaying -> {
                        if (pauseAllowed) {
                            transportControls.pause()
                        }
                    }
                    playbackState.isPlayEnabled -> {
                        transportControls.play()
                    }
                }
            }
        } else {
            transportControls.prepareFromMediaId(mediaItem.mediaId, null)
            transportControls.play()
        }
    }

    fun skipToNext() {
        val transportControls = musicServiceConnection.transportControls
        transportControls.skipToNext()
        val isPrepared = musicServiceConnection.playbackState.value?.isPrepared ?: false
        if (isPrepared) {
            musicServiceConnection.playbackState.value?.let { playbackState ->
                when {
                    playbackState.isPlayEnabled -> transportControls.play()
                }
            }
        }

    }

    fun playMediaId(mediaId: String) {
        val nowPlaying = musicServiceConnection.nowPlaying.value
        val transportControls = musicServiceConnection.transportControls

        val isPrepared = musicServiceConnection.playbackState.value?.isPrepared ?: false
        if (isPrepared && mediaId == nowPlaying?.id) {
            musicServiceConnection.playbackState.value?.let { playbackState ->
                when {
                    playbackState.isPlaying -> transportControls.pause()
                    playbackState.isPlayEnabled -> transportControls.play()
                }
            }
        } else {
            transportControls.playFromMediaId(mediaId, null)
        }
    }

    class Factory(private val musicServiceConnection: MusicServiceConnection) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainActivityViewModel(musicServiceConnection) as T
        }
    }
}