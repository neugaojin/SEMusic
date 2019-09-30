package com.se.senet.base

import com.google.gson.Gson

/**
 *Author: gaojin
 *Time: 2019-09-22 16:41
 */
object GsonFactory {
    val INSTANCE: Gson by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { Gson() }
}