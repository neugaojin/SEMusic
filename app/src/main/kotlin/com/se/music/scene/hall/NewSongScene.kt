package com.se.music.scene.hall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bytedance.scene.Scene
import com.se.music.online.newsong.NewSongExpressBlock
import com.se.music.scene.extend.initViewModel

/**
 *Author: gaojin
 *Time: 2019-10-25 17:19
 */

class NewSongScene : Scene() {

    private lateinit var expressView: NewSongExpressBlock
    private lateinit var viewModel: HallViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {
        expressView = NewSongExpressBlock(sceneContext!!)
        return expressView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = initViewModel()
        viewModel.expressInfo.observe(this, Observer {
            expressView.dataChanged(it)
        })
    }
}