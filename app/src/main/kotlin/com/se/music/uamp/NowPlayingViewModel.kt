package com.se.music.uamp

import android.app.Application
import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.*
import com.se.music.R
import com.se.service.extensions.*
import com.se.service.library.PlayState
import kotlin.math.floor

/**
 *Author: gaojin
 *Time: 2020/4/28 6:32 PM
 */

class NowPlayingViewModel(private val app: Application,
                          musicServiceConnection: MusicServiceConnection
) : AndroidViewModel(app) {
    companion object {
        private const val POSITION_UPDATE_INTERVAL_MILLIS = 100L
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

    private val musicServiceConnection = musicServiceConnection.also {
        it.playbackState.observeForever(playbackStateObserver)
        it.nowPlaying.observeForever(mediaMetadataObserver)
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
        // Update the media button resource ID
        playingState.postValue(
                when (playbackState.isPlaying) {
                    true -> PlayState.PLAYING
                    else -> PlayState.STOP
                }
        )
    }

    override fun onCleared() {
        super.onCleared()
        musicServiceConnection.playbackState.removeObserver(playbackStateObserver)
        musicServiceConnection.nowPlaying.removeObserver(mediaMetadataObserver)

        updatePosition = false
    }

    class Factory(
            private val app: Application,
            private val musicServiceConnection: MusicServiceConnection
    ) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return NowPlayingViewModel(app, musicServiceConnection) as T
        }
    }

}