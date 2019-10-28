package com.se.music.scene.fixed

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bytedance.scene.Scene
import com.bytedance.scene.group.GroupScene

/**
 *Author: gaojin
 *Time: 2019-10-25 14:30
 */

abstract class ScenePageAdapter(scene: GroupScene) : PagerAdapter() {

    private val mGroupScene: GroupScene = scene
    private var mCurrentScene: UserVisibleHintGroupScene? = null

    abstract fun getItem(position: Int): UserVisibleHintGroupScene

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemId = getItemId(position)
        val name = makeFragmentName(itemId)

        val viewPager = container as ViewPager
        var scene = mGroupScene.findSceneByTag<UserVisibleHintGroupScene>(name)
        if (scene != null) {
            // setUserVisibleHint() must be invoked first, as the GroupScene.show() is directly executed.
            configSceneUserVisibleHint(viewPager, scene, position)
            mGroupScene.show(scene)
        } else {
            scene = getItem(position)
            configSceneUserVisibleHint(viewPager, scene, position)
            mGroupScene.add(container.getId(), scene, name)
        }
        return scene
    }

    private fun configSceneUserVisibleHint(viewPager: ViewPager, scene: UserVisibleHintGroupScene?, position: Int) {
        if (mCurrentScene == null) {
            val currentItem = viewPager.currentItem
            if (currentItem == position) {
                mCurrentScene = scene
            }
        }

        val visible = scene === mCurrentScene
        if (scene!!.getUserVisibleHint() != visible) {
            scene.setUserVisibleHint(visible)
        }
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        super.setPrimaryItem(container, position, `object`)
        val scene = `object` as UserVisibleHintGroupScene
        if (scene !== mCurrentScene) {
            if (mCurrentScene != null) {
                mCurrentScene!!.setUserVisibleHint(false)
            }
            scene.setUserVisibleHint(true)
            mCurrentScene = scene
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val scene = `object` as Scene
        mGroupScene.remove(scene)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return (`object` as Scene).view === view
    }

    fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private fun makeFragmentName(id: Long): String {
        return "android:switcher:$id"
    }
}