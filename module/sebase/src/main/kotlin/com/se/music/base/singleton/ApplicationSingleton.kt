package com.se.music.base.singleton

import android.annotation.SuppressLint
import android.app.Application

/**
 *Author: gaojin
 *Time: 2020/4/23 2:19 PM
 */

object ApplicationSingleton {
    @SuppressLint("StaticFieldLeak")
    @Volatile
    lateinit var instance: Application

    fun bindInstance(application: Application) {
        instance = application
    }
}