package com.se.music.scene.local

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bytedance.scene.SceneViewModelProviders
import com.bytedance.scene.group.UserVisibleHintGroupScene
import com.se.music.R
import com.se.music.adapter.MusicListAdapter
import com.se.music.support.coroutine.SeCoroutineScope
import com.se.music.uamp.InjectUtils
import com.se.music.uamp.MediaItemData
import com.se.music.uamp.viewmodel.MainViewModel
import com.se.music.uamp.viewmodel.NowPlayingViewModel
import com.se.music.uamp.viewmodel.SongListViewModel
import com.se.music.widget.loading.LoadingView
import com.se.service.data.MusicCategory
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

    private lateinit var mainViewModel: MainViewModel
    private lateinit var songListViewModel: SongListViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var loadingView: LoadingView
    private lateinit var adapter: MusicListAdapter
    private val scope: SeCoroutineScope by lazy {
        SeCoroutineScope()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): ViewGroup {
        mainViewModel = ViewModelProviders
                .of(activity as FragmentActivity, InjectUtils.provideMainViewModel(sceneContext!!))
                .get(MainViewModel::class.java)

        songListViewModel = SceneViewModelProviders
                .of(this, InjectUtils.provideSongListViewModel(sceneContext!!))
                .get(SongListViewModel::class.java)

        scope.launch {
            delay(DELAY_LOAD_TIME)
            songListViewModel.subscribe(MusicCategory.MUSIC.name)
            songListViewModel.mediaItems.observe(this@LocalSongScene, Observer { data ->
                adapter.setData(data)
                adapter.notifyDataSetChanged()
                loadingView.visibility = View.GONE
                NowPlayingViewModel.setMediaItems(data)
            })
        }
        return inflater.inflate(R.layout.fragment_local_recycler_view, container, false) as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingView = requireViewById(R.id.loading_view)
        recyclerView = requireViewById(R.id.recycler_view)

        adapter = MusicListAdapter()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : MusicListAdapter.OnItemClickListener {
            override fun onItemClick(mediaItem: MediaItemData) {
                mainViewModel.playMedia(mediaItem, true)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        scope.close()
    }
}