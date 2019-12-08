package com.se.music.scene.local

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.se.music.R
import com.se.music.adapter.AlbumListAdapter
import com.se.music.entity.AlbumEntity
import com.se.music.scene.extend.initViewModel
import com.se.music.scene.fixed.UserVisibleHintGroupScene

/**
 *Author: gaojin
 *Time: 2019-12-07 21:43
 */

class LocalAlbumScene : UserVisibleHintGroupScene() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var albumListAdapter: AlbumListAdapter
    private lateinit var localViewModel: LocalViewModel
    private val albumList = ArrayList<AlbumEntity>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): ViewGroup {
        mRecyclerView = inflater.inflate(R.layout.fragment_local_recycler_view, container, false) as RecyclerView
        return mRecyclerView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        localViewModel = initViewModel()
        localViewModel.loadAlbum()
        albumListAdapter = AlbumListAdapter(sceneContext!!, albumList)
        mRecyclerView.run {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = albumListAdapter
        }
        localViewModel.localAlbumInfo.observe(this, Observer {
            albumList.addAll(it)
            albumListAdapter.notifyDataSetChanged()
        })
    }
}