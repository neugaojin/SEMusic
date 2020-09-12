package com.se.music.base.scene

import android.content.Context
import com.bytedance.scene.Scene

/**
 *Author: gaojin
 *Time: 2020/7/10 12:20 AM
 */

fun Scene.baseContext(): Context {
    val context = sceneContext
    if (context != null) {
        return context
    } else {
        throw IllegalArgumentException("Scene is not attached to Activity")
    }
}