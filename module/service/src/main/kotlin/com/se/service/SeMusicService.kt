package com.se.service

import android.app.PendingIntent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat.MediaItem
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.media.MediaBrowserServiceCompat
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.se.music.base.log.Loger
import com.se.service.data.LocalMusicSource
import com.se.service.data.MusicCategory
import com.se.service.data.MusicSource
import com.se.service.extensions.flag
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 *Author: gaojin
 *Time: 2020-02-19 16:24
 */

open class SeMusicService : MediaBrowserServiceCompat() {

    companion object {
        const val TAG = "SeMusicService"
    }

    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var mediaController: MediaControllerCompat
    private lateinit var mediaSessionConnector: MediaSessionConnector

    private var musicSource: MusicSource? = null

    private val uAmpAudioAttributes = AudioAttributes.Builder()
            .setContentType(C.CONTENT_TYPE_MUSIC)
            .setUsage(C.USAGE_MEDIA)
            .build()

    private val exoPlayer: ExoPlayer by lazy {
        SimpleExoPlayer.Builder(this, FfmpegRenderersFactory())
                .build().apply {
                    setAudioAttributes(uAmpAudioAttributes, true)
                }
    }

    private lateinit var playbackPreparer: SePlaybackPreparer

    override fun onCreate() {
        super.onCreate()
        val sessionActivityPendingIntent = packageManager.getLaunchIntentForPackage(packageName).let {
            PendingIntent.getActivity(this, 0, it, 0)
        }

        mediaSession = MediaSessionCompat(this, TAG).apply {
            setSessionActivity(sessionActivityPendingIntent)
            isActive = true
        }

        sessionToken = mediaSession.sessionToken
        mediaController = MediaControllerCompat(this, mediaSession).apply {
            registerCallback(MediaControllerCallback())
        }

        // ExoPlayer will manage the MediaSession for us.
        mediaSessionConnector = MediaSessionConnector(mediaSession).also { connector ->
            val dataSourceFactory = DefaultDataSourceFactory(
                    this, Util.getUserAgent(this, "semusic"), null
            )
            playbackPreparer = SePlaybackPreparer(exoPlayer, dataSourceFactory)
            connector.setPlayer(exoPlayer)
            connector.setPlaybackPreparer(playbackPreparer)
            connector.setQueueNavigator(SeQueueNavigator(mediaSession))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaSession.run {
            isActive = false
            release()
        }
        serviceJob.cancel()
    }

    override fun onGetRoot(clientPackageName: String, clientUid: Int, rootHints: Bundle?): BrowserRoot? {
        return BrowserRoot("/", null)
    }

    override fun onLoadChildren(parentId: String, result: Result<List<MediaItem>>) {
        Loger.d { "parentId : $parentId" }
        musicSource = getMusicSource(parentId) ?: return
        serviceScope.launch {
            musicSource?.load()
        }
        val resultSent = musicSource?.whenReady { successfullyInitialized ->
            if (successfullyInitialized) {
                playbackPreparer.musicSource = musicSource
                val children = musicSource?.map { item ->
                    MediaItem(item.description, item.flag)
                }
                result.sendResult(children)
            } else {
                mediaSession.sendSessionEvent(NETWORK_FAILURE, null)
                result.sendResult(null)
            }
        } ?: false

        if (!resultSent) {
            result.detach()
        }
    }

    private fun getMusicSource(parentId: String): MusicSource? {
        if (parentId == MusicCategory.MUSIC.name) {
            return LocalMusicSource(this)
        }
        return LocalMusicSource(this)
    }

    private inner class MediaControllerCallback : MediaControllerCompat.Callback() {
        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            mediaController.playbackState?.let {
            }
        }

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            state?.let {

            }
        }
    }
}

const val NETWORK_FAILURE = "com.se.music.media.session.NETWORK_FAILURE"