package com.se.music.base

import android.content.Context

/**
 * Author: gaojin
 * Time: 2018/5/7 下午7:26
 */
object BaseConfig {
    private var displayInited: Boolean = false
    var width: Int = 0
    var height: Int = 0
    var density: Float = 0f
    var densityDpi: Int = 0

    fun init(context: Context) {
        initDisplay(context)
    }

    private fun initDisplay(context: Context) {
        if (!displayInited && context.resources != null) {
            val metrics = context.resources.displayMetrics
            width = metrics.widthPixels
            height = metrics.heightPixels
            density = metrics.density
            densityDpi = metrics.densityDpi
            displayInited = true
        }
    }
}