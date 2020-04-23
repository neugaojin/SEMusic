package com.se.music.uamp

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.*

/**
 *Author: gaojin
 *Time: 2020/4/21 8:51 PM
 */

class SceneViewModel(musicServiceConnection: MusicServiceConnection) : ViewModel() {

    private val _mediaItems = MutableLiveData<List<MediaItemData>>()
    val mediaItems: LiveData<List<MediaItemData>> = _mediaItems

    private lateinit var mediaId: String

    val networkError = Transformations.map(musicServiceConnection.networkFailure) { it }

    private val subscriptionCallback = object : MediaBrowserCompat.SubscriptionCallback() {
        override fun onChildrenLoaded(parentId: String, children: MutableList<MediaBrowserCompat.MediaItem>) {
            super.onChildrenLoaded(parentId, children)
            val itemsList = children.map { child ->
                val subtitle = child.description.subtitle ?: ""
                MediaItemData(child.mediaId!!
                        , child.description.title.toString()
                        , subtitle.toString()
                        , child.description.iconUri!!
                        , child.isBrowsable
                        , 0)
            }
            _mediaItems.postValue(itemsList)
        }
    }

    private val playbackStateObserver = Observer<PlaybackStateCompat> {
        val playbackState = it ?: EMPTY_PLAYBACK_STATE
        val metadata = musicServiceConnection.nowPlaying.value ?: NOTHING_PLAYING
        if (metadata.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID) != null) {
//            _mediaItems.postValue()
        }
    }

    private val mediaMetadataObserver = Observer<MediaMetadataCompat> {
        val playbackState = musicServiceConnection.playbackState.value ?: EMPTY_PLAYBACK_STATE
        val metadata = it ?: NOTHING_PLAYING
        if (metadata.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID) != null) {
//            _mediaItems.postValue(updateState(playbackState, metadata))
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

    class Factory(private val musicServiceConnection: MusicServiceConnection) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SceneViewModel(musicServiceConnection) as T
        }
    }
}