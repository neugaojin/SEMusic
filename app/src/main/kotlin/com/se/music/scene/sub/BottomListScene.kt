package com.se.music.scene.sub

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.se.music.R
import com.se.music.adapter.BottomListAdapter
import com.se.music.base.log.Loger
import com.se.music.scene.base.DialogScene
import com.se.music.support.coroutine.SeCoroutineScope
import com.se.music.uamp.InjectUtils
import com.se.music.uamp.viewmodel.MainViewModel
import com.se.music.uamp.viewmodel.NowPlayingViewModel
import com.se.service.library.RepeatMode
import com.se.service.library.RepeatMode.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 *Author: gaojin
 *Time: 2020/4/29 4:17 PM
 */

class BottomListScene : DialogScene() {

    private lateinit var repeatMode: ImageView
    private lateinit var repeatModeText: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BottomListAdapter
    private lateinit var nowPlayingViewModel: NowPlayingViewModel
    private lateinit var mainViewModel: MainViewModel

    private val scope: SeCoroutineScope by lazy {
        SeCoroutineScope()
    }

    override fun onCreateContentView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.scene_bottom_list, container, false)
    }

    private fun initViewModel() {
        nowPlayingViewModel = NowPlayingViewModel.getInstance()

        mainViewModel = ViewModelProviders
                .of(activity as FragmentActivity, InjectUtils.provideMainViewModel(sceneContext!!))
                .get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        requireViewById<View>(R.id.dialog_close_button).setOnClickListener {
            dismiss()
        }
        repeatModeText = requireViewById<TextView>(R.id.dialog_top_title)
        repeatMode = requireViewById<ImageView>(R.id.dialog_repeat_mode).apply {
            setOnClickListener {
                nowPlayingViewModel.changeRepeatMode()
            }
        }
        repeatMode = requireViewById(R.id.dialog_repeat_mode)
        recyclerView = requireViewById(R.id.dialog_list_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        adapter = BottomListAdapter(sceneContext!!)
        adapter.setOnItemClickListener { _, mediaItemData ->
            val mediaItem = mediaItemData ?: return@setOnItemClickListener
            mainViewModel.playMedia(mediaItem)
        }
        adapter.setOnItemDeleteListener { _, queueItem ->
            Loger.e { "setOnItemDeleteListener: $queueItem" }
        }
        recyclerView.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //默认加载的列表
        if (nowPlayingViewModel.mediaItems.value?.isEmpty() == true) {
            scope.launch {
                delay(150)
                //TODO 加载默认列表
            }
        }

        nowPlayingViewModel.mediaItems.observe(this, Observer { data ->
            adapter.list = data
            adapter.notifyDataSetChanged()
            setTopTitleContent(nowPlayingViewModel.repeatMode.value, nowPlayingViewModel.mediaItems.value?.size
                    ?: 0)
        })

        nowPlayingViewModel.mediaMetadata.observe(this, Observer {
            adapter.nowPlayingMediaId = it.id
            adapter.notifyDataSetChanged()
        })

        nowPlayingViewModel.repeatMode.observe(this, Observer { mode ->
            setTopTitleContent(mode, nowPlayingViewModel.mediaItems.value?.size ?: 0)
        })
    }

    @SuppressLint("SetTextI18n")
    fun setTopTitleContent(mode: RepeatMode?, listSize: Int) {
        when (mode) {
            ALL -> {
                repeatMode.setImageDrawable(ContextCompat.getDrawable(sceneContext!!, R.drawable.dialog_repeat_all))
                repeatModeText.text = "${getString(R.string.repeat_mode_all)}(${listSize}首)"
            }
            ONE -> {
                repeatMode.setImageDrawable(ContextCompat.getDrawable(sceneContext!!, R.drawable.dialog_repeat_one))
                repeatModeText.text = getString(R.string.repeat_mode_one)
            }
            SHUFFLE -> {
                repeatMode.setImageDrawable(ContextCompat.getDrawable(sceneContext!!, R.drawable.dialog_repeat_shuffle))
                repeatModeText.text = "${getString(R.string.repeat_mode_shuffle)}(${listSize}首)"
            }
            EMPTY -> {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        scope.close()
    }
}