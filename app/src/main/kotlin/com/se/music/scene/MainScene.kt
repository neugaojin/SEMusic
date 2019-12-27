package com.se.music.scene

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.bytedance.scene.group.GroupScene
import com.bytedance.scene.ktx.activityViewModels
import com.se.music.R
import com.se.music.scene.hall.HallViewModel
import com.se.music.scene.hall.HomeHallScene
import com.se.music.scene.mine.HomeFindScene
import com.se.music.scene.mine.HomeMineScene
import com.se.music.support.utils.setupWithViewPager
import com.se.music.widget.loading.LoadingView

/**
 *Author: gaojin
 *Time: 2019-10-18 14:25
 */

class MainScene : GroupScene(), View.OnClickListener, ViewPager.OnPageChangeListener {

    companion object {
        const val TAG = "MainScene"
        const val MINE = 0
        const val MUSIC = 1
        const val FIND = 2
    }

    private lateinit var mineView: TextView
    private lateinit var musicRoomView: TextView
    private lateinit var findView: TextView

    private lateinit var viewPager: ViewPager

    private lateinit var loadingView: LoadingView

    private val viewModel: HallViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): ViewGroup {
        return inflater.inflate(R.layout.fragment_app_main, container, false) as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = requireViewById(R.id.view_pager)
        mineView = requireViewById(R.id.tool_bar_mine)
        musicRoomView = requireViewById(R.id.tool_bar_music_room)
        findView = requireViewById(R.id.tool_bar_find)
        loadingView = requireViewById(R.id.loading_view)

        mineView.setOnClickListener(this)
        musicRoomView.setOnClickListener(this)
        findView.setOnClickListener(this)
        viewPager.addOnPageChangeListener(this)

        viewModel.fetchData()
        viewModel.notification.observe(this, Observer {
            if (it) {
                loadingView.visibility = View.GONE
                val childScene = listOf(HomeMineScene(), HomeHallScene(), HomeFindScene())
                setupWithViewPager(viewPager, this, childScene)
                setTitleStyle(MUSIC)
                viewPager.offscreenPageLimit = 2
                viewPager.currentItem = 1
            }
        })
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
            MINE -> {
                mineView.textSize = 19f
                mineView.setTextColor(ContextCompat.getColor(sceneContext!!, R.color.white))

                musicRoomView.textSize = 18f
                musicRoomView.setTextColor(ContextCompat.getColor(sceneContext!!, R.color.light_gray))

                findView.textSize = 18f
                findView.setTextColor(ContextCompat.getColor(sceneContext!!, R.color.light_gray))
            }
            MUSIC -> {
                musicRoomView.textSize = 19f
                musicRoomView.setTextColor(ContextCompat.getColor(sceneContext!!, R.color.white))

                mineView.textSize = 18f
                mineView.setTextColor(ContextCompat.getColor(sceneContext!!, R.color.light_gray))

                findView.textSize = 18f
                findView.setTextColor(ContextCompat.getColor(sceneContext!!, R.color.light_gray))
            }
            FIND -> {
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