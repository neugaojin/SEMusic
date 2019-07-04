package com.se.music.singleton

import com.google.gson.Gson

/**
 * Created by gaojin on 2018/1/1.
 */
class GsonFactory {
    companion object {
        val instance: Gson by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { Gson() }
    }
}