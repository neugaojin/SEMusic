package com.se.music.scene.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bytedance.scene.group.UserVisibleHintGroupScene
import com.bytedance.scene.interfaces.PushOptions
import com.bytedance.scene.ktx.viewModels
import com.se.music.R
import com.se.music.base.data.database.entity.SongListEntity
import com.se.music.base.scene.baseContext
import com.se.music.scene.local.LocalMainScene

/**
 *Author: gaojin
 *Time: 2019-10-21 15:44
 */

class HomeMineScene : UserVisibleHintGroupScene(), HeaderViewOperation {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterV2: MineAdapterV2
    private lateinit var headerView: MineHeaderView
    private val mineViewModel: HomeMineViewModel by viewModels()
    private val list = mutableListOf<SongListEntity>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): ViewGroup {
        val rootView: View = inflater.inflate(R.layout.fragment_mine_mvp, container, false)
        recyclerView = rootView.findViewById(R.id.mine_recycler_view)
        headerView = MineHeaderView(baseContext())
        headerView.headerViewOperation = this
        //init adapter
        repeat(30) {
            list.add(SongListEntity("1", "周杰伦", "00000000"))
        }
        adapterV2 = MineAdapterV2(baseContext(), list)
        adapterV2.addHeader(headerView)

        recyclerView.layoutManager = LinearLayoutManager(baseContext())
        recyclerView.adapter = adapterV2
        return rootView as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mineViewModel.musicCount.observe(this, Observer {
            val infoList = listOf(it.toString(), "2", "3", "4", "5")
            headerView.update(infoList)
        })
    }

    override fun startLocalMusic() {
        val options = PushOptions.Builder().setAnimation(requireActivity(), R.anim.slide_right_in, R.anim.slide_left_out).build()
        requireNavigationScene().push(LocalMainScene::class.java, null, options)
    }

    override fun startDownLoadMusic() {
    }

    override fun startRecentMusic() {
    }

    override fun startLoveMusic() {
    }

    override fun startRunningRadio() {
    }
}