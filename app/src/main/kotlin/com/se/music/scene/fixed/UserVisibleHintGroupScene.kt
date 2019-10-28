package com.se.music.scene.fixed

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.NonNull
import androidx.annotation.RestrictTo
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.bytedance.scene.group.GroupScene
import androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP
import com.bytedance.scene.Scene

/**
 *Author: gaojin
 *Time: 2019-10-25 14:35
 */

abstract class UserVisibleHintGroupScene : GroupScene() {
    /**
     * @hide
     */
    @RestrictTo(LIBRARY_GROUP)
    val KEY_SCENE_USER_VISIBLE_HINT = "bd-scene-nav:scene_user_visible_hint"

    private val mUserVisibleLifecycleOwner = UserVisibleLifecycleOwner()

    private var mUserVisibleHint = true
    private var mResume = false
    private var mStart = false

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_SCENE_USER_VISIBLE_HINT, this.mUserVisibleHint)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            this.mUserVisibleHint = savedInstanceState.getBoolean(KEY_SCENE_USER_VISIBLE_HINT)
        }
    }

    fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (this.mUserVisibleHint == isVisibleToUser) {
            return
        }
        mUserVisibleHint = isVisibleToUser
        dispatchVisibleChanged()

        if (mUserVisibleHint) {
            if (mStart) {
                mUserVisibleLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_START)
            }
            if (mResume) {
                mUserVisibleLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
            }
        } else {
            if (mResume) {
                mUserVisibleLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            }

            if (mStart) {
                mUserVisibleLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mUserVisibleLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun onPause() {
                mResume = false
                if (mUserVisibleHint) {
                    mUserVisibleLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun onResume() {
                mResume = true
                if (mUserVisibleHint) {
                    mUserVisibleLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun onStop() {
                mStart = false
                if (mUserVisibleHint) {
                    mUserVisibleLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onStart() {
                mStart = true
                if (mUserVisibleHint) {
                    mUserVisibleLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_START)
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onDestroy() {
                mUserVisibleLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            }
        })
    }

    fun getUserVisibleHint(): Boolean {
        return mUserVisibleHint
    }

    fun getUserVisibleHintLifecycle(): Lifecycle {
        return this.mUserVisibleLifecycleOwner.lifecycle
    }

    override fun isVisible(): Boolean {
        return super.isVisible() && mUserVisibleHint
    }
}