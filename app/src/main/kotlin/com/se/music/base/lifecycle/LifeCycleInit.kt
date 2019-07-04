package com.se.music.base.lifecycle

import android.app.Application

/**
 *Author: gaojin
 *Time: 2018/10/18 下午3:34
 */

class LifeCycleInit {
    fun init(app: Application) {
        app.registerActivityLifecycleCallbacks(LifecycleCallbacks.INSTANCE)
    }
}