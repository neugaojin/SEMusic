package com.se.music.scene.fixed

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

/**
 *Author: gaojin
 *Time: 2019-10-25 14:45
 */

class UserVisibleLifecycleOwner : LifecycleOwner {
    private var mLifecycleRegistry: LifecycleRegistry? = null

    /**
     * Initializes the underlying Lifecycle if it hasn't already been created.
     */
    private fun initialize() {
        if (mLifecycleRegistry == null) {
            mLifecycleRegistry = LifecycleRegistry(this)
        }
    }

    override fun getLifecycle(): Lifecycle {
        initialize()
        return mLifecycleRegistry!!
    }

    fun handleLifecycleEvent(event: Lifecycle.Event) {
        initialize()
        mLifecycleRegistry!!.handleLifecycleEvent(event)
    }
}