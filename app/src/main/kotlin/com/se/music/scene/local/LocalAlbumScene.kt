package com.se.music.scene.local

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bytedance.scene.group.UserVisibleHintGroupScene
import com.se.music.R
import com.se.music.adapter.AlbumListAdapter
import com.se.music.base.data.database.entity.AlbumEntity
import com.se.music.support.coroutine.SeCoroutineScope
import com.se.music.base.data.DataBase
import com.se.music.base.scene.baseContext
import com.se.music.widget.loading.LoadingView
import kotlinx.coroutines.launch

/**
 *Author: gaojin
 *Time: 2019-12-07 21:43
 */

class LocalAlbumScene : UserVisibleHintGroupScene() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AlbumListAdapter
    private lateinit var loadingView: LoadingView

    private val albumList = ArrayList<AlbumEntity>()
    private val scope: SeCoroutineScope by lazy {
        SeCoroutineScope()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): ViewGroup {
        return inflater.inflate(R.layout.fragment_local_recycler_view, container, false) as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingView = requireViewById(R.id.loading_view)
        recyclerView = requireViewById(R.id.recycler_view)

        adapter = AlbumListAdapter(baseContext(), albumList)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (albumList.isEmpty() && isVisibleToUser) {
            scope.launch {
                albumList.addAll(DataBase.queryLocalAlbum())
                adapter.notifyDataSetChanged()
                loadingView.visibility = View.GONE
            }
        }
    }
}