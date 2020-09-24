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
 *Author: gaojin
 *Time: 2020/4/23 9:41 PM
 */

class SePlaybackPreparer(private val exoPlayer: ExoPlayer, private val dataSourceFactory: DataSource.Factory) : MediaSessionConnector.PlaybackPreparer {

    var musicSource: MusicSource? = null
    override fun onPrepareFromSearch(query: String, playWhenReady: Boolean, extras: Bundle) = Unit

    override fun onCommand(player: Player, controlDispatcher: ControlDispatcher, command: String, extras: Bundle?, cb: ResultReceiver?): Boolean = false

    override fun onPrepareFromUri(uri: Uri, playWhenReady: Boolean, extras: Bundle) = Unit

    override fun onPrepare(playWhenReady: Boolean) = Unit


    override fun getSupportedPrepareActions(): Long =
            PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID or
                    PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID or
                    PlaybackStateCompat.ACTION_SKIP_TO_NEXT or
                    PlaybackStateCompat.ACTION_SET_REPEAT_MODE

    override fun onPrepareFromMediaId(mediaId: String, playWhenReady: Boolean, extras: Bundle) {
        Loger.e { "onPrepareFromMediaId: $mediaId" }
        musicSource?.let { source ->
            val itemToPlay: MediaMetadataCompat? = source.find { item ->
                item.id == mediaId
            }
            if (itemToPlay != null) {
                val metadataList = source.map { it }
                val mediaSource = metadataList.toMediaSource(dataSourceFactory)
                val initialWindowIndex = metadataList.indexOf(itemToPlay)
                Loger.e { "prepare mediaSource: $mediaSource" }
                exoPlayer.prepare(mediaSource)
                exoPlayer.seekTo(initialWindowIndex, 0)
            }
        }
    }
}