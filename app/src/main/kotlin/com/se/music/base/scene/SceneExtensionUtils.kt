package com.se.music.base.scene

import android.content.Context
import com.bytedance.scene.Scene
import com.se.music.base.singleton.ApplicationSingleton

/**
 *Author: gaojin
 *Time: 2020/7/10 12:20 AM
 */

fun Scene.baseContext(): Context {
    var context = sceneContext
    if (context == null) {
        context = ApplicationSingleton.instance
    }
    return context
}