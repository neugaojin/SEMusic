package com.se.music.support.singleton

import android.annotation.SuppressLint
import android.app.Application
import android.os.Handler

/**
 * Created by gaojin on 2018/1/1.
 */
object HandlerSingleton {
    val INSTANCE: Handler by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { Handler() }
}

object ApplicationSingleton {
    @SuppressLint("StaticFieldLeak")
    @Volatile
    lateinit var instance: Application

    fun bindInstance(application: Application) {
        instance = application
    }
}