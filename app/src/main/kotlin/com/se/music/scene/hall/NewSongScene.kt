package com.se.music.scene.hall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bytedance.scene.Scene
import com.bytedance.scene.ktx.activityViewModels
import com.bytedance.scene.ktx.viewModels
import com.se.music.online.newsong.NewSongExpressBlock

/**
 *Author: gaojin
 *Time: 2019-10-25 17:19
 */

class NewSongScene : Scene() {

    private lateinit var expressView: NewSongExpressBlock
    private val viewModel: HallViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {
        expressView = NewSongExpressBlock(sceneContext!!)
        return expressView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.expressInfo.observe(this, Observer { data ->
            if (data != null) {
                view.visibility = View.VISIBLE
                expressView.dataChanged(data)
            } else {
                view.visibility = View.GONE
            }
        })
    }
}