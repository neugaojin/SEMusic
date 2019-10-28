package com.se.music.scene.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.se.music.R
import com.se.music.scene.fixed.UserVisibleHintGroupScene

/**
 *Author: gaojin
 *Time: 2019-10-21 16:32
 */

class HomeFindScene : UserVisibleHintGroupScene() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): ViewGroup {
        return inflater.inflate(R.layout.fragment_local_folder, container, false) as ViewGroup
    }
}