package com.se.music.scene.hall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bytedance.scene.Scene
import com.bytedance.scene.ktx.activityViewModels
import com.bytedance.scene.ktx.viewModels
import com.se.music.base.scene.baseContext
import com.se.music.online.recommend.RecommendSongListBlock

/**
 *Author: gaojin
 *Time: 2019-10-25 16:15
 */

class RecommendScene : Scene() {
    private val viewModel: HallViewModel by activityViewModels()
    private lateinit var recommendView: RecommendSongListBlock

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {
        recommendView = RecommendSongListBlock(baseContext())
        return recommendView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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