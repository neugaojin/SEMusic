package com.se.music.service

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.os.PowerManager
import android.util.Log
import com.se.music.base.Null
import com.se.music.base.singleton.ApplicationSingleton
import java.io.IOException
import java.lang.ref.WeakReference

/**
 *Author: gaojin
 *Time: 2018/9/16 下午7:34
 *播放器实现
 */

class MultiPlayer(service: MediaService, private val mHandler: Handler) {

    private val mService: WeakReference<MediaService> = WeakReference(service)
    private val handler = Handler()

    var isPlayerPrepared = false
    private var isNextPlayerPrepared = false
    /**
     * 播放器是否初始化
     */
    var isInitialized = false
    var secondaryPosition = 0
    // 两个播放器
    private var mCurrentMediaPlayer = MediaPlayer()
    private var mNextMediaPlayer: MediaPlayer? = null
    private var isPlayerNet = false
    private var mIsNextInitialized = false
    private var mIllegalState = false
    private var preparedNextListener = MediaPlayer.OnPreparedListener { isNextPlayerPrepared = true }
    private var mNextMediaPath = Null
    private var isFirstLoad = true

    private val audioAttributes = AudioAttributes.Builder().apply {
        setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
        setFlags(AudioAttributes.CONTENT_TYPE_MUSIC)
        setUsage(AudioAttributes.USAGE_MEDIA)
        setLegacyStreamType(AudioManager.STREAM_MUSIC)
    }.build()

    private val preparedListener = MediaPlayer.OnPreparedListener { mp ->
        if (isFirstLoad) {
            val seekPos = mService.get()?.mLastSeekPos ?: 0
            seek(seekPos)
            isFirstLoad = false
        }
        mp.setOnCompletionListener(completionListener)
        isPlayerPrepared = true
    }

    private val bufferingUpdateListener = MediaPlayer.OnBufferingUpdateListener { _, percent ->
        if (secondaryPosition != 100)
            mService.get()?.sendUpdateBuffer(percent)
        secondaryPosition = percent
    }

    private val errorListener = MediaPlayer.OnErrorListener { _, what, _ ->
        when (what) {
            MediaPlayer.MEDIA_ERROR_SERVER_DIED -> {
                isInitialized = false
                isPlayerPrepared = false
                mCurrentMediaPlayer.release()
                mCurrentMediaPlayer = MediaPlayer()
                mCurrentMediaPlayer.setWakeMode(service, PowerManager.PARTIAL_WAKE_LOCK)

                val errorInfo = TrackErrorInfo(mService.get()?.getAudioId()
                        ?: 0, mService.get()?.getTrackName())
                val msg = mHandler.obtainMessage(MediaService.SERVER_DIED, errorInfo)
                mHandler.sendMessageDelayed(msg, 2000)
                return@OnErrorListener true
            }
        }
        return@OnErrorListener false
    }

    private val completionListener = MediaPlayer.OnCompletionListener { mp ->
        isPlayerPrepared = false
        if (mp === mCurrentMediaPlayer && mNextMediaPlayer != null) {
            mCurrentMediaPlayer.release()
            mCurrentMediaPlayer = mNextMediaPlayer!!
            isPlayerPrepared = true
            mNextMediaPlayer = null
            mNextMediaPath = Null
            mHandler.sendEmptyMessage(MediaService.TRACK_WENT_TO_NEXT)
        } else {
            mHandler.sendEmptyMessage(MediaService.TRACK_ENDED)
            mHandler.sendEmptyMessage(MediaService.RELEASE_WAKELOCK)
        }
    }

    private val startMediaPlayerIfPrepared = object : Runnable {
        override fun run() {
            if (isPlayerPrepared) {
                mCurrentMediaPlayer.start()
                val duration = duration()
                if (mService.get()?.mRepeatMode != MediaService.REPEAT_CURRENT && duration > 2000 &&
                        position() >= duration - 2000) {
                    mService.get()?.nextPlay()
                }
                mService.get()?.loading(false)
            } else {
                handler.postDelayed(this, 700)
            }
        }
    }

    init {
        mCurrentMediaPlayer.setWakeMode(mService.get(), PowerManager.PARTIAL_WAKE_LOCK)
    }

    fun setDataSource(path: String) {
        isInitialized = setDataSourceImpl(mCurrentMediaPlayer, path)
        if (isInitialized) {
            setNextDataSource(null)
        }
    }

    private fun setDataSourceImpl(player: MediaPlayer, path: String): Boolean {
        isPlayerNet = false
        isPlayerPrepared = false
        try {
            player.reset()
            player.setAudioAttributes(audioAttributes)
            if (path.startsWith("content://")) {
                player.setOnPreparedListener(null)
                player.setDataSource(ApplicationSingleton.instance, Uri.parse(path))
                player.prepare()
                player.setOnCompletionListener(completionListener)
                isPlayerPrepared = true
            } else {
                player.setDataSource(path)
                player.setOnPreparedListener(preparedListener)
                player.prepareAsync()
                isPlayerNet = true
            }
            if (mIllegalState) {
                mIllegalState = false
            }
        } catch (ignored: IOException) {
            return false
        } catch (ignored: IllegalArgumentException) {
            return false
        } catch (todo: IllegalStateException) {
            todo.printStackTrace()
            if (!mIllegalState) {
                mCurrentMediaPlayer = MediaPlayer()
                mCurrentMediaPlayer.setWakeMode(mService.get(), PowerManager.PARTIAL_WAKE_LOCK)
                setDataSourceImpl(mCurrentMediaPlayer, path)
                mIllegalState = true
            } else {
                mIllegalState = false
                return false
            }
        }
        player.setOnErrorListener(errorListener)
        player.setOnBufferingUpdateListener(bufferingUpdateListener)
        return true
    }

    fun setNextDataSource(path: String?) {
        mIsNextInitialized = false
        try {
            mCurrentMediaPlayer.setNextMediaPlayer(null)
        } catch (ignored: IllegalArgumentException) {
            Log.i(MediaService.TAG, "Next media player is current one, continuing")
        } catch (ignored: IllegalStateException) {
            Log.e(MediaService.TAG, "Media player not initialized!")
            return
        }

        if (mNextMediaPlayer != null) {
            mNextMediaPlayer!!.release()
            mNextMediaPlayer = null
        }
        if (path == null || path.isEmpty()) {
            return
        }
        mNextMediaPlayer = MediaPlayer()
        mNextMediaPlayer!!.setWakeMode(mService.get(), PowerManager.PARTIAL_WAKE_LOCK)

        if (setNextDataSourceImpl(mNextMediaPlayer!!, path)) {
            mNextMediaPath = path
            mCurrentMediaPlayer.setNextMediaPlayer(mNextMediaPlayer)
        } else {
            mNextMediaPlayer!!.release()
        }
    }

    private fun setNextDataSourceImpl(player: MediaPlayer, path: String): Boolean {
        isNextPlayerPrepared = false
        try {
            player.reset()
            player.setAudioAttributes(audioAttributes)
            if (path.startsWith("content://")) {
                player.setOnPreparedListener(preparedNextListener)
                player.setDataSource(ApplicationSingleton.instance, Uri.parse(path))
                player.prepare()
            } else {
                player.setDataSource(path)
                player.setOnPreparedListener(preparedNextListener)
                player.prepare()
                isNextPlayerPrepared = false
            }
        } catch (ignored: IOException) {
            return false
        } catch (ignored: IllegalArgumentException) {
            return false
        }

        player.setOnCompletionListener(completionListener)
        player.setOnErrorListener(errorListener)
        return true
    }

    fun start() {
        if (isPlayerNet) {
            // 播放网络音乐
            secondaryPosition = 0
            mService.get()?.loading(true)
            handler.postDelayed(startMediaPlayerIfPrepared, 50)
        } else {
            // 播放本地音乐
            mService.get()?.sendUpdateBuffer(100)
            secondaryPosition = 100
            mCurrentMediaPlayer.start()
        }
    }

    fun stop() {
        handler.removeCallbacks(startMediaPlayerIfPrepared)
        mCurrentMediaPlayer.reset()
        isInitialized = false
        isPlayerPrepared = false
    }

    fun release() {
        mCurrentMediaPlayer.release()
    }

    fun pause() {
        handler.removeCallbacks(startMediaPlayerIfPrepared)
        mCurrentMediaPlayer.pause()
    }

    fun duration(): Long {
        return if (isPlayerPrepared) {
            mCurrentMediaPlayer.duration.toLong()
        } else -1
    }

    fun position(): Long {
        if (isPlayerPrepared) {
            try {
                return mCurrentMediaPlayer.currentPosition.toLong()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return -1
    }

    fun seek(whereto: Long): Long {
        mCurrentMediaPlayer.seekTo(whereto.toInt())
        return whereto
    }
}