package com.se.music.scene

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bytedance.scene.group.GroupScene
import com.se.music.R
import com.se.music.scene.hall.HomeHallScene

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
        add(R.id.se_main_content, MainScene(), MainScene::class.java.simpleName)
        add(R.id.bottom_container, BottomFixedScene(), BottomFixedScene::class.java.simpleName)
    }
}