package com.se.music

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import com.se.music.base.BaseConfig
import com.se.music.base.Null
import com.se.music.singleton.ApplicationSingleton

/**
 * Author: gaojin
 * Time: 2018/5/6 下午2:38
 * 两个独立的进程，所以MyApplication被初始化了两次
 */
class MusicApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        ApplicationSingleton.bindInstance(this)
    }

    override fun onCreate() {
        super.onCreate()
        val processName = getProcessName(this, android.os.Process.myPid())
        if (processName == packageName) {
            onCreateInit()
        }
    }

    private fun onCreateInit() {
        BaseConfig.init(this)
    }

    private fun getProcessName(cxt: Context, pid: Int): String {
        val am = cxt.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningApps = am.runningAppProcesses ?: return Null
        for (procInfo in runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName
            }
        }
        return Null
    }
}