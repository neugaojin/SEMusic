package com.se.music.base.scene

import android.content.Context
import androidx.annotation.NonNull
import com.bytedance.scene.Scene
import com.bytedance.scene.interfaces.PushOptions
import com.se.music.R

/**
 *Author: gaojin
 *Time: 2020/7/10 12:20 AM
 */

fun Scene.baseContext(): Context {
    val context = sceneContext
    if (context != null) {
        return context
    } else {
        throw IllegalArgumentException("Scene is not attached to Activity")
    }
}

fun Scene.pushWithAnim(@NonNull clazz: Class<out Scene>) {
    requireNavigationScene().push(clazz, null,
            PushOptions.Builder()
                    .setAnimation(requireActivity(), R.anim.slide_right_in, R.anim.slide_left_out)
                    .build())
}