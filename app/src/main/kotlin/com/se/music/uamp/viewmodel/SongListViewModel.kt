package com.se.music.uamp.viewmodel

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.*
import com.se.music.uamp.EMPTY_PLAYBACK_STATE
import com.se.music.uamp.MediaItemData
import com.se.music.uamp.MusicServiceConnection
import com.se.music.uamp.NOTHING_PLAYING
import com.se.service.extensions.id
import com.se.service.extensions.isPlaying
import com.se.service.library.PlayState

/**
 *Author: gaojin
 *Time: 2020/4/21 8:51 PM
 */

class SongListViewModel(musicServiceConnection: MusicServiceConnection) : ViewModel() {

    private val _mediaItems = MutableLiveData<List<MediaItemData>>().apply {
        value = emptyList()
    }
    val mediaItems: LiveData<List<MediaItemData>> = _mediaItems

    private lateinit var mediaId: String

    val networkError = Transformations.map(musicServiceConnection.networkFailure) { it }

    private val subscriptionCallback = object : MediaBrowserCompat.SubscriptionCallback() {
        override fun onChildrenLoaded(parentId: String, children: MutableList<MediaBrowserCompat.MediaItem>) {
            super.onChildrenLoaded(parentId, children)
            val itemsList = children.map { child ->
                MediaItemData(child.mediaId!!
                        , child.description.title.toString()
                        , child.description.subtitle.toString()
                        , child.description.description.toString()
                        , child.description.iconUri!!
                        , child.isBrowsable
                        , PlayState.EMPTY)
            }
            _mediaItems.postValue(itemsList)
        }
    }

    private val playbackStateObserver = Observer<PlaybackStateCompat> {
        val playbackState = it ?: EMPTY_PLAYBACK_STATE
        val metadata = musicServiceConnection.nowPlaying.value ?: NOTHING_PLAYING
        if (metadata.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID) != null) {
            _mediaItems.postValue(updateState(playbackState, metadata))
        }
    }

    private val mediaMetadataObserver = Observer<MediaMetadataCompat> {
        val playbackState = musicServiceConnection.playbackState.value ?: EMPTY_PLAYBACK_STATE
        val metadata = it ?: NOTHING_PLAYING
        if (metadata.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID) != null) {
            _mediaItems.postValue(updateState(playbackState, metadata))
        }
    }

    private val musicServiceConnection = musicServiceConnection.also {
        it.playbackState.observeForever(playbackStateObserver)
        it.nowPlaying.observeForever(mediaMetadataObserver)
    }

    fun subscribe(parentId: String) {
        mediaId = parentId
        musicServiceConnection.subscribe(parentId, subscriptionCallback)
    }

    override fun onCleared() {
        super.onCleared()
        musicServiceConnection.playbackState.removeObserver(playbackStateObserver)
        musicServiceConnection.nowPlaying.removeObserver(mediaMetadataObserver)
        musicServiceConnection.unsubscribe(mediaId, subscriptionCallback)
    }

    private fun updateState(
            playbackState: PlaybackStateCompat,
            mediaMetadata: MediaMetadataCompat
    ): List<MediaItemData> {

        val playState = when (playbackState.isPlaying) {
            true -> PlayState.PLAYING
            else -> PlayState.PAUSE
        }

        return mediaItems.value?.map {
            val state = if (it.mediaId == mediaMetadata.id) playState else PlayState.EMPTY
            it.copy(playbackState = state)
        } ?: emptyList()
    }

    class Factory(private val musicServiceConnection: MusicServiceConnection) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SongListViewModel(musicServiceConnection) as T
        }
    }
}