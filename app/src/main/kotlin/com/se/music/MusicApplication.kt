package com.se.music

import android.app.Application
import android.content.Context
import android.util.Log
import com.se.music.base.singleton.ApplicationSingleton

/**
 * Author: gaojin
 * Time: 2018/5/6 下午2:38
 * 两个独立的进程，所以MyApplication被初始化了两次
 */
class MusicApplication : Application() {
    companion object {
        const val APP_NAME = "SeMusic"
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Log.e("gj", "ApplicationSingleton.bindInstance")
        ApplicationSingleton.bindInstance(this)
    }
}