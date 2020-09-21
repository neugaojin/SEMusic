package com.se.music.scene.playing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.bytedance.scene.group.GroupScene
import com.se.music.R
import com.se.music.widget.MultiButtonLayout
import com.se.music.widget.PlayingAlbumPageTransformer

/**
 *Author: gaojin
 *Time: 2020/5/13 7:15 PM
 */

class PlayingMainScene : GroupScene(), ViewPager.OnPageChangeListener {
    companion object {
        const val TAG = "PlayingMainScene"
    }

    private lateinit var toolbar: Toolbar
    private lateinit var songTitle: TextView
    private lateinit var viewPager: ViewPager
    private lateinit var multiButtonLayout: MultiButtonLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): ViewGroup {
        return inflater.inflate(R.layout.scene_playing_main, container, false) as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarInit()
        songTitle = requireViewById(R.id.playing_song_title)
        viewPager = requireViewById(R.id.content_view_pager)
        multiButtonLayout = requireViewById(R.id.playing_select_radio)

        viewPager.run {
            setPageTransformer(false, PlayingAlbumPageTransformer())
            addOnPageChangeListener(this@PlayingMainScene)
            offscreenPageLimit = 2
        }
    }

    private fun toolbarInit() {
        toolbar = requireViewById(R.id.playing_toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        val supportActionBar = (activity as AppCompatActivity).supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        multiButtonLayout.setSelectedChild(position)
    }
}