package com.se.music.base.log

import android.util.Log
import com.se.music.base.BuildConfig

/**
 *Author: gaojin
 *Time: 2019-12-05 18:26
 */

object Loger {

    /**
     * 屏蔽所有的Log
     */
    private const val LEVEL_MIN = 0
    /**
     * 输出所有Log
     */
    private const val LEVEL_MAX = Log.ASSERT
    /**
     * Log最低输出的层级
     */
    private var minLevel = LEVEL_MIN
    private var appTag = "Loger"

    fun init(appName: String) {
        minLevel = if (BuildConfig.DEBUG) LEVEL_MIN else LEVEL_MAX
        appTag = appName
    }

    private fun logout(tag: String, msg: String, type: Int) {
        if (type < minLevel) {
            return
        }
        when (type) {
            Log.VERBOSE -> Log.v(tag, msg)
            Log.DEBUG -> Log.d(tag, msg)
            Log.INFO -> Log.i(tag, msg)
            Log.WARN -> Log.w(tag, msg)
            Log.ERROR -> Log.e(tag, msg)
        }
    }

    fun v(tag: String, msg: () -> Any?) {
        logout(tag, msg.toStringSafe(), Log.VERBOSE)
    }

    fun v(msg: () -> Any?) {
        logout(appTag, msg.toStringSafe(), Log.VERBOSE)
    }

    fun d(tag: String, msg: () -> Any?) {
        logout(tag, msg.toStringSafe(), Log.DEBUG)
    }

    fun d(msg: () -> Any?) {
        logout(appTag, msg.toStringSafe(), Log.DEBUG)
    }

    fun i(tag: String, msg: () -> Any?) {
        logout(tag, msg.toStringSafe(), Log.INFO)
    }

    fun i(msg: () -> Any?) {
        logout(appTag, msg.toStringSafe(), Log.INFO)
    }

    fun w(tag: String, msg: () -> Any?) {
        logout(tag, msg.toStringSafe(), Log.WARN)
    }

    fun w(msg: () -> Any?) {
        logout(appTag, msg.toStringSafe(), Log.WARN)
    }

    fun e(tag: String, msg: () -> Any?) {
        logout(tag, msg.toStringSafe(), Log.ERROR)
    }

    fun e(msg: () -> Any?) {
        logout(appTag, msg.toStringSafe(), Log.ERROR)
    }
}