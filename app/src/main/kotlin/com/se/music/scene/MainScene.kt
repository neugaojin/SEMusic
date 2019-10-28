package com.se.music.scene

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.bytedance.scene.group.GroupScene
import com.bytedance.scene.ui.GroupSceneUtility
import com.se.music.R
import com.se.music.fragment.MainFragment
import com.se.music.scene.hall.HomeHallScene
import com.se.music.scene.mine.HomeFindScene
import com.se.music.scene.mine.HomeMineScene
import com.se.music.utils.setupWithViewPager

/**
 *Author: gaojin
 *Time: 2019-10-18 14:25
 */

class MainScene : GroupScene(), View.OnClickListener, ViewPager.OnPageChangeListener {

    private lateinit var mineView: TextView
    private lateinit var musicRoomView: TextView
    private lateinit var findView: TextView

    private lateinit var viewPager: ViewPager

    private val childScene = listOf(HomeMineScene(), HomeHallScene(), HomeFindScene())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): ViewGroup {
        val rootGroup = inflater.inflate(R.layout.fragment_app_main, container, false) as ViewGroup
        viewPager = rootGroup.findViewById(R.id.view_pager)
        mineView = rootGroup.findViewById(R.id.tool_bar_mine)
        musicRoomView = rootGroup.findViewById(R.id.tool_bar_music_room)
        findView = rootGroup.findViewById(R.id.tool_bar_find)

        mineView.setOnClickListener(this)
        musicRoomView.setOnClickListener(this)
        findView.setOnClickListener(this)
        viewPager.addOnPageChangeListener(this)
        return rootGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWithViewPager(viewPager, this, childScene)
        setTitleStyle(MainFragment.MUSIC)
        viewPager.offscreenPageLimit = 2
        viewPager.currentItem = 1
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.tool_bar_mine -> {
                viewPager.currentItem = 0
                setTitleStyle(0)
            }
            R.id.tool_bar_music_room -> {
                viewPager.currentItem = 1
                setTitleStyle(1)
            }
            R.id.tool_bar_find -> {
                viewPager.currentItem = 2
                setTitleStyle(2)
            }
        }
    }

    private fun setTitleStyle(position: Int) {
        when (position) {
            MainFragment.MINE -> {
                mineView.textSize = 19f
                mineView.setTextColor(ContextCompat.getColor(sceneContext!!, R.color.white))

                musicRoomView.textSize = 18f
                musicRoomView.setTextColor(ContextCompat.getColor(sceneContext!!, R.color.light_gray))

                findView.textSize = 18f
                findView.setTextColor(ContextCompat.getColor(sceneContext!!, R.color.light_gray))
            }
            MainFragment.MUSIC -> {
                musicRoomView.textSize = 19f
                musicRoomView.setTextColor(ContextCompat.getColor(sceneContext!!, R.color.white))

                mineView.textSize = 18f
                mineView.setTextColor(ContextCompat.getColor(sceneContext!!, R.color.light_gray))

                findView.textSize = 18f
                findView.setTextColor(ContextCompat.getColor(sceneContext!!, R.color.light_gray))
            }
            MainFragment.FIND -> {
                findView.textSize = 19f
                findView.setTextColor(ContextCompat.getColor(sceneContext!!, R.color.white))

                mineView.textSize = 18f
                mineView.setTextColor(ContextCompat.getColor(sceneContext!!, R.color.light_gray))

                musicRoomView.textSize = 18f
                musicRoomView.setTextColor(ContextCompat.getColor(sceneContext!!, R.color.light_gray))
            }
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        setTitleStyle(position)
    }
}