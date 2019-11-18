package com.se.music.scene.hall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bytedance.scene.Scene
import com.se.music.online.recommend.RecommendSongListBlock
import com.se.music.scene.extend.initViewModel

/**
 *Author: gaojin
 *Time: 2019-10-25 16:15
 */

class RecommendScene : Scene() {

    private lateinit var viewModel: HallViewModel
    private lateinit var recommendView: RecommendSongListBlock

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {
        recommendView = RecommendSongListBlock(sceneContext!!)
        return recommendView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = initViewModel()
        viewModel.recommendList.observe(this, Observer { data ->
            if (data != null) {
                view.visibility = View.VISIBLE
                recommendView.dataChanged(data)
            } else {
                view.visibility = View.GONE
            }
        })
    }
}