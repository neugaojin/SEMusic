package com.se.music.scene.hall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import com.se.music.R
import com.se.music.online.event.ScrollEvent
import com.se.music.scene.extend.add
import com.se.music.scene.extend.initViewModel
import com.se.music.scene.fixed.UserVisibleHintGroupScene

/**
 *Author: gaojin
 *Time: 2019-10-21 16:07
 */

class HomeHallScene : UserVisibleHintGroupScene(), NestedScrollView.OnScrollChangeListener {

    private lateinit var viewModel: HallViewModel

    private val scrollEvent = ScrollEvent(0f, 0f)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): ViewGroup {
        val rootView = inflater.inflate(R.layout.fragment_music_mvp_v2, container, false) as NestedScrollView
        rootView.setOnScrollChangeListener(this)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = initViewModel()
    }

    override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
        scrollEvent.dy = scrollY.toFloat()
        viewModel.scrollEvent.value = scrollEvent
    }
}