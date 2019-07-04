package com.se.music.base.lifecycle

import android.app.Activity

/**
 *Author: gaojin
 *Time: 2018/10/18 下午2:19
 */

class LifecycleCallbacks : ActivityLifecycleCallbacksAdapter() {

    companion object {
        val INSTANCE: LifecycleCallbacks
                by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { LifecycleCallbacks() }
    }

    private val mApplicationSwitchMonitors = mutableListOf<ApplicationSwitchMonitor>()
    private val mApplicationDestroyMonitors = mutableListOf<ApplicationDestroyMonitor>()

    private var count = 0

    fun addApplicationSwitchMonitor(applicationSwitchMonitor: ApplicationSwitchMonitor) {
        mApplicationSwitchMonitors.add(applicationSwitchMonitor)
    }

    fun removeApplicationSwitchMonitor(applicationSwitchMonitor: ApplicationSwitchMonitor) {
        mApplicationSwitchMonitors.remove(applicationSwitchMonitor)
    }

    fun addApplicationDestroyMonitor(applicationDestroyMonitor: ApplicationDestroyMonitor) {
        mApplicationDestroyMonitors.add(applicationDestroyMonitor)
    }

    fun removeApplicationDestroyMonitor(applicationDestroyMonitor: ApplicationDestroyMonitor) {
        mApplicationDestroyMonitors.remove(applicationDestroyMonitor)
    }

    override fun onActivityStarted(activity: Activity?) {
        if (count <= 0) {
            count = 0
            mApplicationSwitchMonitors.forEach {
                it.applicationEnterForeground()
            }
        }
        count++
    }

    override fun onActivityStopped(activity: Activity?) {
        count--
        if (count <= 0) {
            count = 0
            mApplicationSwitchMonitors.forEach {
                it.applicationEnterBackground()
            }
        }
    }

    override fun onActivityDestroyed(activity: Activity?) {
        if (count <= 0) {
            count = 0
            mApplicationDestroyMonitors.forEach {
                it.applicationDestroyed()
            }
        }
    }
}