package com.se.music.uamp

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.MutableLiveData
import com.se.music.base.singleton.ApplicationSingleton
import com.se.music.uamp.util.getRepeatMode
import com.se.service.NETWORK_FAILURE
import com.se.service.SeMusicService
import com.se.service.library.RepeatMode

/**
 *Author: gaojin
 *Time: 2020-01-13 22:00
 */

class MusicServiceConnection private constructor(context: Context, serviceComponent: ComponentName) {

    companion object {
        fun getInstance() = Inner.singleton

        private object Inner {
            val singleton = MusicServiceConnection(ApplicationSingleton.instance, ComponentName(ApplicationSingleton.instance, SeMusicService::class.java))
        }
    }

    private val mediaBrowserConnectionCallback = MediaBrowserConnectionCallback(context)

    private val mediaBrowser = MediaBrowserCompat(context, serviceComponent, mediaBrowserConnectionCallback, null).apply { connect() }

    val playbackState = MutableLiveData<PlaybackStateCompat>().apply { postValue(EMPTY_PLAYBACK_STATE) }

    val isConnected = MutableLiveData<Boolean>().apply { postValue(false) }

    val networkFailure = MutableLiveData<Boolean>().apply { postValue(false) }

    val nowPlaying = MutableLiveData<MediaMetadataCompat>().apply { postValue(NOTHING_PLAYING) }

    val nowRepeatMode = MutableLiveData<RepeatMode>().apply { postValue(RepeatMode.EMPTY) }

    val transportControls: MediaControllerCompat.TransportControls
        get() = mediaController.transportControls

    private lateinit var mediaController: MediaControllerCompat

    fun subscribe(parentId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
        mediaBrowser.subscribe(parentId, callback)
    }

    fun unsubscribe(parentId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
        mediaBrowser.unsubscribe(parentId, callback)
    }

    fun getMediaController(): MediaControllerCompat {
        return mediaController
    }

    private inner class MediaBrowserConnectionCallback(private val context: Context) : MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            mediaController = MediaControllerCompat(context, mediaBrowser.sessionToken).apply {
                registerCallback(MediaControllerCallback())
                transportControls.apply {
                    transportControls.setShuffleMode(PlaybackStateCompat.SHUFFLE_MODE_NONE)
                    transportControls.setRepeatMode(PlaybackStateCompat.REPEAT_MODE_ALL)
                }
            }
            isConnected.postValue(true)
            nowRepeatMode.postValue(getRepeatMode(mediaController.repeatMode, mediaController.shuffleMode))
        }

        override fun onConnectionSuspended() {
            isConnected.postValue(false)
        }

        override fun onConnectionFailed() {
            isConnected.postValue(false)
        }
    }

    private inner class MediaControllerCallback : MediaControllerCompat.Callback() {
        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            playbackState.postValue(state ?: EMPTY_PLAYBACK_STATE)
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            nowPlaying.postValue(metadata ?: NOTHING_PLAYING)
        }

        override fun onQueueChanged(queue: MutableList<MediaSessionCompat.QueueItem>?) {
        }

        override fun onRepeatModeChanged(repeatMode: Int) {
            nowRepeatMode.postValue(getRepeatMode(repeatMode, mediaController.shuffleMode))
        }

        override fun onShuffleModeChanged(shuffleMode: Int) {
            nowRepeatMode.postValue(getRepeatMode(mediaController.repeatMode, shuffleMode))
        }

        override fun onSessionEvent(event: String?, extras: Bundle?) {
            when (event) {
                NETWORK_FAILURE -> networkFailure.postValue(true)
            }
        }

        override fun onSessionDestroyed() {
            mediaBrowserConnectionCallback.onConnectionSuspended()
        }
    }
}

val EMPTY_PLAYBACK_STATE: PlaybackStateCompat = PlaybackStateCompat.Builder()
        .setState(PlaybackStateCompat.STATE_NONE, 0, 0f)
        .build()

val NOTHING_PLAYING: MediaMetadataCompat = MediaMetadataCompat.Builder()
        .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, "")
        .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, 0)
        .build()