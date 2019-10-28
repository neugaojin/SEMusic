package com.se.music.scene.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.se.music.R
import com.se.music.entity.SongListEntity
import com.se.music.mine.event.CollectEvent
import com.se.music.mine.event.CreateEvent
import com.se.music.mine.root.MineAdapter
import com.se.music.scene.fixed.UserVisibleHintGroupScene

/**
 *Author: gaojin
 *Time: 2019-10-21 15:44
 */

class HomeMineScene : UserVisibleHintGroupScene() {

    private lateinit var recyclerView: RecyclerView
    private val list = mutableListOf<SongListEntity>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): ViewGroup {
        val rootView: View = inflater.inflate(R.layout.fragment_mine_mvp, container, false)
        recyclerView = rootView.findViewById(R.id.mine_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(sceneContext)
        repeat(30) {
            list.add(SongListEntity("1", "周杰伦", "00000000"))
        }
        recyclerView.adapter = MineAdapterV1(sceneContext!!, list)
        return rootView as ViewGroup
    }
}