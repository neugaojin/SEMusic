package com.se.music.scene.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.bytedance.scene.Scene
import com.bytedance.scene.interfaces.PushOptions
import com.bytedance.scene.navigation.NavigationScene
import com.se.music.R
import com.se.music.scene.animation.BottomDialogSceneAnimatorExecutor

/**
 *Author: gaojin
 *Time: 2020/4/29 5:00 PM
 */

abstract class DialogScene : Scene() {
    companion object {
        fun show(navigationScene: NavigationScene, clazz: Class<out Scene>) {
            navigationScene.push(clazz
                    , null
                    , PushOptions.Builder()
                    .setTranslucent(true)
                    .setAnimation(BottomDialogSceneAnimatorExecutor())
                    .build())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {
        val rootContainer = FrameLayout(sceneContext!!)
        val content = onCreateContentView(inflater, rootContainer, savedInstanceState).apply {
            id = R.id.scene_dialog_content_view
        }
        content.setOnTouchListener { _, _ -> true }
        rootContainer.addView(content)
        rootContainer.setOnClickListener {
            requireNavigationScene().pop()
        }
        return rootContainer
    }

    abstract fun onCreateContentView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View

    protected fun dismiss() {
        requireNavigationScene().pop()
    }
}