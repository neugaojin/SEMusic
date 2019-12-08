package com.se.music.scene

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bytedance.scene.group.GroupScene
import com.bytedance.scene.navigation.NavigationScene
import com.bytedance.scene.navigation.NavigationSceneOptions
import com.se.music.R

/**
 *Author: gaojin
 *Time: 2019-10-20 21:21
 */

class RootScene : GroupScene() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): ViewGroup {
        return inflater.inflate(R.layout.activity_main, container, false) as ViewGroup
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //MainScene is a NavigationScene
        val mainNavigationScene = NavigationScene()
        val options = NavigationSceneOptions(MainScene::class.java, null)
        mainNavigationScene.setArguments(options.toBundle())
        add(R.id.se_main_content, mainNavigationScene, MainScene.TAG)
        add(R.id.bottom_container, BottomFixedScene(), BottomFixedScene.TAG)
    }
}