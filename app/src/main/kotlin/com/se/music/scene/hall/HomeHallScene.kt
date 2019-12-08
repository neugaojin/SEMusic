package com.se.music.scene.hall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import com.bytedance.scene.ktx.viewModels
import com.se.music.R
import com.se.music.online.event.ScrollEvent
import com.se.music.scene.fixed.UserVisibleHintGroupScene

/**
 *Author: gaojin
 *Time: 2019-10-21 16:07
 */

class HomeHallScene : UserVisibleHintGroupScene(), NestedScrollView.OnScrollChangeListener {

    private val viewModel: HallViewModel by viewModels()

    private val scrollEvent = ScrollEvent(0f, 0f)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): ViewGroup {
        val rootView = inflater.inflate(R.layout.fragment_music_mvp_v2, container, false) as NestedScrollView
        rootView.setOnScrollChangeListener(this)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        requireViewById<ScenePlaceHolderView>(R.id.banner).apply {
//            sceneName = BannerScene::class.java.name
//            sceneTag = BannerScene::class.java.simpleName
//        }
//
//        requireViewById<ScenePlaceHolderView>(R.id.classify_view).apply {
//            sceneName = COTScene::class.java.name
//            sceneTag = COTScene::class.java.simpleName
//        }
//
//        requireViewById<ScenePlaceHolderView>(R.id.online_recommend).apply {
//            sceneName = RecommendScene::class.java.name
//            sceneTag = RecommendScene::class.java.simpleName
//        }
//
//        requireViewById<ScenePlaceHolderView>(R.id.online_express).apply {
//            sceneName = RecommendScene::class.java.name
//            sceneTag = RecommendScene::class.java.simpleName
//        }
    }

    override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
        scrollEvent.dy = scrollY.toFloat()
        viewModel.scrollEvent.value = scrollEvent
    }
}