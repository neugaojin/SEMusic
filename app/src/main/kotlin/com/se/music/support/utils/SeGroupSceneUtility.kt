package com.se.music.support.utils

import androidx.viewpager.widget.ViewPager
import com.bytedance.scene.group.GroupScene
import com.bytedance.scene.group.UserVisibleHintGroupScene
import com.bytedance.scene.ui.ScenePageAdapter

/**
 *Author: gaojin
 *Time: 2019-10-22 15:20
 */

fun setupWithViewPager(viewPager: ViewPager, groupScene: GroupScene, children: List<UserVisibleHintGroupScene>) {
    require(viewPager.adapter == null) { "ViewPager already have a adapter" }
    val scenePageAdapter = object : ScenePageAdapter(groupScene) {
        override fun getCount(): Int {
            return children.size
        }

        override fun getItem(position: Int): UserVisibleHintGroupScene {
            return children[position]
        }
    }
    viewPager.adapter = scenePageAdapter
}

fun setupWithViewPagerWithTitle(viewPager: ViewPager
                                , groupScene: GroupScene
                                , children: List<UserVisibleHintGroupScene>
                                , title: List<String>) {
    require(viewPager.adapter == null) { "ViewPager already have a adapter" }
    val scenePageAdapter = object : ScenePageAdapter(groupScene) {
        override fun getCount(): Int {
            return children.size
        }

        override fun getItem(position: Int): UserVisibleHintGroupScene {
            return children[position]
        }

//        override fun setPageTitle(list: List<String>) {
//            super.setPageTitle(title)
//        }
    }
    viewPager.adapter = scenePageAdapter
}