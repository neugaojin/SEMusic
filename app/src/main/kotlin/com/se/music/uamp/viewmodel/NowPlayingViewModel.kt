package com.se.music.uamp.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.se.music.R
import com.se.music.base.singleton.ApplicationSingleton
import com.se.music.uamp.EMPTY_PLAYBACK_STATE
import com.se.music.uamp.InjectUtils
import com.se.music.uamp.MediaItemData
import com.se.music.uamp.MusicServiceConnection
import com.se.music.uamp.NOTHING_PLAYING
import com.se.service.extensions.albumArtUri
import com.se.service.extensions.currentPlayBackPosition
import com.se.service.extensions.displaySubtitle
import com.se.service.extensions.duration
import com.se.service.extensions.id
import com.se.service.extensions.isPlaying
import com.se.service.extensions.title
import com.se.service.library.PlayState
import com.se.service.library.RepeatMode
import kotlin.math.floor

/**
 *Author: gaojin
 *Time: 2020/5/13 10:51 AM
 */

class NowPlayingViewModel(private val app: Application,
                          musicServiceConnection: MusicServiceConnection) {
    companion object {
        private const val POSITION_UPDATE_INTERVAL_MILLIS = 100L

        @Volatile
        private var instance: NowPlayingViewModel? = null
        fun getInstance() =
                instance ?: synchronized(this) {
                    instance ?: NowPlayingViewModel(
                            ApplicationSingleton.instance,
                            InjectUtils.getMSC(ApplicationSingleton.instance)
                    ).also {
                        instance = it
                    }
                }

        fun setMediaItems(mediaItems: List<MediaItemData>) {
            getInstance().mediaItems.postValue(mediaItems)
        }
    }

    data class NowPlayingMetadata(
            val id: String,
            val albumArtUri: Uri,
            val title: String?,
            val subtitle: String?,
            val duration: String
    ) {
        companion object {
            fun timestampToMSS(context: Context, position: Long): String {
                val totalSeconds = floor(position / 1E3).toInt()
                val minutes = totalSeconds / 60
                val remainingSeconds = totalSeconds - (minutes * 60)
                return if (position < 0) context.getString(R.string.duration_unknown)
                else context.getString(R.string.duration_format).format(minutes, remainingSeconds)
            }
        }
    }

    private var updatePosition = false
    private val handler = Handler(Looper.getMainLooper())

    private var playbackState: PlaybackStateCompat = EMPTY_PLAYBACK_STATE
    val mediaMetadata = MutableLiveData<NowPlayingMetadata>()
    val mediaPosition = MutableLiveData<Long>().apply {
        postValue(0L)
    }
    val playingState = MutableLiveData<PlayState>().apply {
        postValue(PlayState.EMPTY)
    }

    val repeatMode = MutableLiveData<RepeatMode>().apply {
        postValue(RepeatMode.EMPTY)
    }

    val mediaItems = MutableLiveData<List<MediaItemData>>().apply {
        value = emptyList()
    }

    private val playbackStateObserver = Observer<PlaybackStateCompat> {
        playbackState = it ?: EMPTY_PLAYBACK_STATE
        val metadata = musicServiceConnection.nowPlaying.value ?: NOTHING_PLAYING
        updateState(playbackState, metadata)
        if (playbackState.isPlaying) {
            updatePosition = true
            checkPlaybackPosition()
        } else {
            updatePosition = false
        }
    }

    private val mediaMetadataObserver = Observer<MediaMetadataCompat> {
        updateState(playbackState, it)
    }

    private val repeatModeObserver = Observer<RepeatMode> {
        repeatMode.postValue(it)
    }

    private val musicServiceConnection = musicServiceConnection.apply {
        playbackState.observeForever(playbackStateObserver)
        nowPlaying.observeForever(mediaMetadataObserver)
        nowRepeatMode.observeForever(repeatModeObserver)
        checkPlaybackPosition()
    }

    private fun checkPlaybackPosition(): Boolean = handler.postDelayed({
        val currPosition = playbackState.currentPlayBackPosition
        if (mediaPosition.value != currPosition)
            mediaPosition.postValue(currPosition)
        if (updatePosition)
            checkPlaybackPosition()
    }, POSITION_UPDATE_INTERVAL_MILLIS)


    private fun updateState(
            playbackState: PlaybackStateCompat,
            mediaMetadata: MediaMetadataCompat
    ) {
        // Only update media item once we have duration available
        if (mediaMetadata.duration != 0L) {
            val nowPlayingMetadata = NowPlayingMetadata(
                    mediaMetadata.id,
                    mediaMetadata.albumArtUri,
                    mediaMetadata.title?.trim(),
                    mediaMetadata.displaySubtitle?.trim(),
                    NowPlayingMetadata.timestampToMSS(app, mediaMetadata.duration)
            )
            this.mediaMetadata.postValue(nowPlayingMetadata)
        }
        // Update the play state
        playingState.postValue(
                when (playbackState.isPlaying) {
                    true -> PlayState.PLAYING
                    else -> PlayState.STOP
                }
        )
    }

    fun changeRepeatMode() {
        var value = repeatMode.value?.value ?: return
        value = (++value) % 3
        when (value) {
            RepeatMode.ALL.value -> {
                musicServiceConnection.transportControls.apply {
                    setRepeatMode(PlaybackStateCompat.REPEAT_MODE_ALL)
                    setShuffleMode(PlaybackStateCompat.SHUFFLE_MODE_NONE)
                }
            }
            RepeatMode.ONE.value -> {
                musicServiceConnection.transportControls.apply {
                    setRepeatMode(PlaybackStateCompat.REPEAT_MODE_ONE)
                    setShuffleMode(PlaybackStateCompat.SHUFFLE_MODE_NONE)
                }
            }
            RepeatMode.SHUFFLE.value -> {
                musicServiceConnection.transportControls.apply {
                    setRepeatMode(PlaybackStateCompat.REPEAT_MODE_ALL)
                    setShuffleMode(PlaybackStateCompat.SHUFFLE_MODE_ALL)
                }
            }
        }
    }
}