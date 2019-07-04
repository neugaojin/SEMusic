package com.se.music.singleton

import android.os.Handler

/**
 * Author: gaojin
 * Time: 2018/5/6 下午5:25
 */
class HandlerSingleton : Handler() {
    companion object {
        val instance: HandlerSingleton by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { HandlerSingleton() }
    }
}