package com.se.music.scene.sub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.se.music.R
import com.se.music.adapter.LocalFragmentAdapter
import com.se.music.scene.base.BaseScene

/**
 *Author: gaojin
 *Time: 2019-10-21 15:10
 */

class LocalMusicScene : BaseScene() {

    private lateinit var mAdapter: LocalFragmentAdapter
    private var position: Int = 0
    private lateinit var mTabLayout: TabLayout
    private lateinit var mViewPager: ViewPager

    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View {

        val content = inflater.inflate(R.layout.fragment_local_music, container, false)
        mTabLayout = content.findViewById(R.id.local_tab_layout)
        mViewPager = content.findViewById(R.id.local_view_pager)
        return content
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(sceneContext?.getString(R.string.local_music_title))
//        mLoaderManager.run {
//            initLoader(QUERY_LOCAL_SONG, null, buildLocalMusicCallBack())
//            initLoader(QUERY_LOCAL_SINGER, null, buildLocalSingerCallBack())
//            initLoader(QUERY_LOCAL_ALBUM, null, buildLocalAlbumCallBack())
//            initLoader(QUERY_FOLDER, null, buildFolderCallBack())
//        }

//        mAdapter = LocalFragmentAdapter(fm, context!!)
//        mViewPager.adapter = mAdapter
//        mViewPager.currentItem = position
//        mTabLayout.setupWithViewPager(mViewPager)
    }
}