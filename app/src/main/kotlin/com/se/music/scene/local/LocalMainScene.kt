package com.se.music.scene.local

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.bytedance.scene.group.GroupScene
import com.google.android.material.tabs.TabLayout
import com.se.music.R
import com.se.music.scene.base.SeCompatScene
import com.se.music.utils.setupWithViewPagerWithTitle

/**
 *Author: gaojin
 *Time: 2019-12-07 21:40
 *本地音乐主页面
 */

class LocalMainScene : SeCompatScene() {

    private lateinit var mTabLayout: TabLayout
    private lateinit var mViewPager: ViewPager

    private val childScene = listOf(LocalSongScene(), LocalArtistScene(), LocalAlbumScene())

    override fun createContentView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {
        val content = LayoutInflater.from(sceneContext).inflate(R.layout.fragment_local_music, container, false)
        mTabLayout = content.findViewById(R.id.local_tab_layout)
        mViewPager = content.findViewById(R.id.local_view_pager)
        return content
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(getString(R.string.local_music_title))
        val tabName = mutableListOf(getString(R.string.local_music_song, 0)
                , getString(R.string.local_music_singer, 0),
                getString(R.string.local_music_album, 0),
                getString(R.string.local_music_folder, 0))
        setupWithViewPagerWithTitle(mViewPager, this, childScene, tabName)
        mTabLayout.setupWithViewPager(mViewPager)
    }
}