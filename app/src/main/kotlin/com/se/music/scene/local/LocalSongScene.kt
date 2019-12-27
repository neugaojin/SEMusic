package com.se.music.scene.local

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bytedance.scene.group.UserVisibleHintGroupScene
import com.se.music.R
import com.se.music.adapter.MusicListAdapter
import com.se.music.entity.MusicEntity
import com.se.music.support.coroutine.SeCoroutineScope
import com.se.music.support.database.DataBase
import com.se.music.support.singleton.ApplicationSingleton
import com.se.music.widget.loading.LoadingView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *Author: gaojin
 *Time: 2019-12-07 21:42
 */

class LocalSongScene : UserVisibleHintGroupScene() {

    companion object {
        const val DELAY_LOAD_TIME = 250L
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var loadingView: LoadingView
    private lateinit var adapter: MusicListAdapter
    private val musicList = mutableListOf<MusicEntity>()
    private val scope: SeCoroutineScope by lazy {
        SeCoroutineScope()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): ViewGroup {
        return inflater.inflate(R.layout.fragment_local_recycler_view_v1, container, false) as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingView = requireViewById(R.id.loading_view)
        recyclerView = requireViewById(R.id.recycler_view)
        adapter = MusicListAdapter(sceneContext!!, musicList)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        if (musicList.isEmpty()) {
            scope.launch {
                delay(DELAY_LOAD_TIME)
                musicList.addAll(DataBase.queryLocalSong())
                adapter.notifyDataSetChanged()
                loadingView.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        scope.close()
    }
}