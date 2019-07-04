package com.se.music.base.lifecycle

/**
 *Author: gaojin
 *Time: 2018/10/18 下午2:05
 */

interface ApplicationSwitchMonitor {
    fun applicationEnterForeground()

    fun applicationEnterBackground()
}