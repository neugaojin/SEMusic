package com.se.music.scene.local

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.se.music.R
import com.se.music.adapter.MusicListAdapter
import com.se.music.entity.MusicEntity
import com.se.music.scene.extend.initViewModel
import com.se.music.scene.fixed.UserVisibleHintGroupScene

/**
 *Author: gaojin
 *Time: 2019-12-07 21:42
 */

class LocalSongScene : UserVisibleHintGroupScene() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var adapter: MusicListAdapter
    private lateinit var localViewModel: LocalViewModel
    private val musicList = mutableListOf<MusicEntity>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): ViewGroup {
        mRecyclerView = inflater.inflate(R.layout.fragment_local_recycler_view, container, false) as RecyclerView
        return mRecyclerView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        localViewModel = initViewModel()
        localViewModel.loadMusic()
        adapter = MusicListAdapter(sceneContext!!, musicList)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.adapter = adapter
        localViewModel.localSongInfo.observe(this, Observer {
            musicList.addAll(it)
            adapter.notifyDataSetChanged()
        })
    }
}