package com.se.music.singleton

import android.annotation.SuppressLint
import android.app.Application

/**
 * Author: gaojin
 * Time: 2018/5/6 下午7:14
 */
class ApplicationSingleton {
    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        lateinit var instance: Application

        fun bindInstance(application: Application) {
            instance = application
        }
    }
}