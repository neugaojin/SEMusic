package com.se.music.scene.extend

import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.bytedance.scene.Scene
import com.bytedance.scene.group.GroupScene

/**
 *Author: gaojin
 *Time: 2019-10-25 16:57
 */

inline fun <reified T : ViewModel> Scene.initViewModel(): T {
    return ViewModelProviders.of(activity as FragmentActivity).get(T::class.java)
}

fun GroupScene.add(@IdRes viewId: Int, @NonNull scene: Scene) {
    add(viewId, scene, scene::class.java.simpleName)
}