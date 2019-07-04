package com.se.music.utils.manager

import android.os.Handler
import com.se.music.service.MusicPlayer

/**
 *Author: gaojin
 *Time: 2018/10/24 下午11:41
 *全局播放时间管理
 */

class GlobalPlayTimeManager {

    companion object {
        private val INSTANCE: GlobalPlayTimeManager by
        lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { GlobalPlayTimeManager() }

        fun registerListener(listener: PlayTimeListener) {
            INSTANCE.registerPlayTimeListener(listener)
        }

        fun unregisterListener(listener: PlayTimeListener) {
            INSTANCE.unregisterPlayTimeListener(listener)
        }

        fun trigger() {
            INSTANCE.trigger()
        }
    }

    private val list = ArrayList<PlayTimeListener>()
    private val handler = Handler()

    private val updatePlayTimeRunnable = object : Runnable {
        override fun run() {
            val position = MusicPlayer.position()
            val duration = MusicPlayer.duration()
            if (duration in 1..627080715) {
                list.forEach {
                    it.playedTime(position, duration)
                }
            }
            if (MusicPlayer.isPlaying()) {
                handler.postDelayed(this, 200)
            } else {
                handler.removeCallbacks(this)
            }
        }
    }

    fun trigger() {
        if (MusicPlayer.isPlaying()) {
            handler.post(updatePlayTimeRunnable)
        } else {
            handler.removeCallbacks(updatePlayTimeRunnable)
        }
    }

    fun registerPlayTimeListener(listener: PlayTimeListener) {
        list.add(listener)
    }

    fun unregisterPlayTimeListener(listener: PlayTimeListener) {
        list.remove(listener)
    }
}