package com.se.music.scene.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bytedance.scene.group.GroupScene
import com.se.music.R

/**
 *Author: gaojin
 *Time: 2019-10-25 18:17
 */

class MineHeadScene : GroupScene() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): ViewGroup {
        val header: View = inflater.inflate(R.layout.mine_header_view_layout, container, false)
        return header as ViewGroup
    }
}