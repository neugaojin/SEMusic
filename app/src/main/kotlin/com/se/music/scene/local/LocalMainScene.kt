package com.se.music.scene.local

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.se.music.R
import com.se.music.scene.base.SeCompatScene
import com.se.music.support.coroutine.SeCoroutineScope
import com.se.music.support.database.DataBase
import com.se.music.support.utils.setupWithViewPagerWithTitle
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 *Author: gaojin
 *Time: 2019-12-07 21:40
 *本地音乐主页面
 */

class LocalMainScene : SeCompatScene() {

    private lateinit var mTabLayout: TabLayout
    private lateinit var mViewPager: ViewPager
    private val tabName = mutableListOf<String>()

    private val childScene = listOf(LocalSongScene(), LocalArtistScene(), LocalAlbumScene())
    private val scope: SeCoroutineScope by lazy {
        SeCoroutineScope()
    }

    override fun createContentView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {
        return LayoutInflater.from(sceneContext).inflate(R.layout.fragment_local_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(getString(R.string.local_music_title))
        mTabLayout = requireViewById(R.id.local_tab_layout)
        mViewPager = requireViewById(R.id.local_view_pager)
        mViewPager.offscreenPageLimit = 2

        tabName.apply {
            add(getString(R.string.local_music_song, 0))
            add(getString(R.string.local_music_singer, 0))
            add(getString(R.string.local_music_album, 0))
        }
        setupWithViewPagerWithTitle(mViewPager, this, childScene, tabName)
        mTabLayout.setupWithViewPager(mViewPager)
    }

    override fun onResume() {
        super.onResume()
        scope.launch {
            val songCount = async { DataBase.queryLocalSongCount() }
            val singerCount = async { DataBase.queryLocalArtistCount() }
            val albumCount = async { DataBase.queryLocalAlbumCount() }
            tabName[0] = getString(R.string.local_music_song, songCount.await())
            tabName[1] = getString(R.string.local_music_singer, singerCount.await())
            tabName[2] = getString(R.string.local_music_album, albumCount.await())
            mViewPager.adapter?.notifyDataSetChanged()
        }
    }
}