package com.se.music.scene.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.se.music.R
import com.se.music.entity.SongListEntity
import com.se.music.scene.extend.initViewModel
import com.se.music.scene.fixed.UserVisibleHintGroupScene

/**
 *Author: gaojin
 *Time: 2019-10-21 15:44
 */

class HomeMineScene : UserVisibleHintGroupScene() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterV2: MineAdapterV2
    private lateinit var headerView: MineHeaderView
    private lateinit var mineViewModel: HomeMineViewModel
    private val list = mutableListOf<SongListEntity>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): ViewGroup {
        val rootView: View = inflater.inflate(R.layout.fragment_mine_mvp, container, false)
        recyclerView = rootView.findViewById(R.id.mine_recycler_view)
        headerView = MineHeaderView(sceneContext!!)
        //init adapter
        repeat(30) {
            list.add(SongListEntity("1", "周杰伦", "00000000"))
        }
        adapterV2 = MineAdapterV2(sceneContext!!, list)
        adapterV2.addHeader(headerView)

        //init recyclerView
        recyclerView.layoutManager = LinearLayoutManager(sceneContext)
        recyclerView.adapter = adapterV2
        return rootView as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mineViewModel = initViewModel()
        mineViewModel.musicCount.observe(this, Observer {
            val infoList = listOf(it.toString(), "2", "3", "4", "5")
            headerView.update(infoList)
        })
    }
}