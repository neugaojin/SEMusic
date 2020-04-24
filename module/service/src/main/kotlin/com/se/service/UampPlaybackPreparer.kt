/*
 * Copyright 2018 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.se.service

import android.net.Uri
import android.os.Bundle
import android.os.ResultReceiver
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.google.android.exoplayer2.ControlDispatcher
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.upstream.DataSource
import com.se.music.base.log.Loger
import com.se.service.data.MusicSource
import com.se.service.extensions.id
import com.se.service.extensions.toMediaSource

/**
 * Class to bridge UAMP to the ExoPlayer MediaSession extension.
 */
class UampPlaybackPreparer(private val exoPlayer: ExoPlayer
                           , private val dataSourceFactory: DataSource.Factory) : MediaSessionConnector.PlaybackPreparer {

    var musicSource: MusicSource? = null
    override fun onPrepareFromSearch(query: String, playWhenReady: Boolean, extras: Bundle) = Unit

    override fun onCommand(player: Player, controlDispatcher: ControlDispatcher, command: String, extras: Bundle?, cb: ResultReceiver?): Boolean = false

    override fun onPrepareFromUri(uri: Uri, playWhenReady: Boolean, extras: Bundle) = Unit

    override fun onPrepare(playWhenReady: Boolean) = Unit


    override fun getSupportedPrepareActions(): Long =
            PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID or
                    PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID or
                    PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH or
                    PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH

    override fun onPrepareFromMediaId(mediaId: String, playWhenReady: Boolean, extras: Bundle) {
        Loger.e { "onPrepareFromMediaId: $mediaId" }
        musicSource ?: return
        val itemToPlay: MediaMetadataCompat? = musicSource!!.find { item ->
            item.id == mediaId
        }
        if (itemToPlay != null) {
            val metadataList = musicSource!!.map { it }
            val mediaSource = metadataList.toMediaSource(dataSourceFactory)
            val initialWindowIndex = metadataList.indexOf(itemToPlay)
            Loger.e { "prepare mediaSource: $mediaSource" }
            exoPlayer.prepare(mediaSource)
            exoPlayer.seekTo(initialWindowIndex, 0)
        }
    }
}

private const val TAG = "MediaSessionHelper"
