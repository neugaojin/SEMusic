package com.se.service.data

import android.provider.MediaStore
import android.support.v4.media.MediaMetadataCompat
import androidx.annotation.IntDef

/**
 *Author: gaojin
 *Time: 2020-02-19 16:39
 */

interface MusicSource : Iterable<MediaMetadataCompat> {
    suspend fun load()
    fun whenReady(action: (Boolean) -> Unit): Boolean
}

/**
 * 资源创建
 */
const val STATE_CREATED = 1

/**
 * 资源正在初始化中
 */
const val STATE_INITIALIZING = 2

/**
 * 资源初始化完毕
 */
const val STATE_INITIALIZED = 3

/**
 * 状态错误
 */
const val STATE_ERROR = 4

@IntDef(STATE_CREATED,
        STATE_INITIALIZING,
        STATE_INITIALIZED,
        STATE_ERROR)
@Retention(AnnotationRetention.SOURCE)
annotation class State


abstract class AbstractMusicSource : MusicSource {
    private val onReadyListeners = mutableListOf<(Boolean) -> Unit>()
    @State
    var state: Int = STATE_CREATED
        set(value) {
            if (value == STATE_INITIALIZED || value == STATE_ERROR) {
                synchronized(onReadyListeners) {
                    field = value
                    onReadyListeners.forEach { it -> it(state == STATE_INITIALIZED) }
                }
            } else {
                field = value
            }
        }


    override fun whenReady(action: (Boolean) -> Unit): Boolean =
            when (state) {
                STATE_CREATED, STATE_INITIALIZING -> {
                    onReadyListeners.add(action)
                    false
                }
                else -> {
                    action(state != STATE_ERROR)
                    true
                }
            }
}