package com.se.music.scene.playing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bytedance.scene.group.GroupScene
import com.se.music.R
import com.se.music.base.scene.baseContext
import com.se.music.support.utils.ms2Minute
import com.se.music.uamp.InjectUtils
import com.se.music.uamp.viewmodel.MainViewModel
import com.se.music.uamp.viewmodel.NowPlayingViewModel
import com.se.service.library.PlayState
import com.se.service.library.RepeatMode

/**
 *Author: gaojin
 *Time: 2020/5/13 7:15 PM
 */

class PlayingBottomScene : GroupScene(), SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    companion object {
        const val TAG = "PlayingBottomScene"
    }

    private lateinit var repeatMode: ImageView
    private lateinit var preSong: ImageView
    private lateinit var centerControl: ImageView
    private lateinit var nextSong: ImageView
    private lateinit var songList: ImageView
    private lateinit var favorite: ImageView
    private lateinit var download: ImageView
    private lateinit var share: ImageView
    private lateinit var comment: ImageView

    private lateinit var seekTimePlayed: TextView
    private lateinit var seekTotalTime: TextView
    private lateinit var seekBar: AppCompatSeekBar

    private lateinit var playingViewModel: NowPlayingViewModel
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): ViewGroup {
        return inflater.inflate(R.layout.block_playing_bottom, container, false) as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playingViewModel = NowPlayingViewModel.getInstance()

        mainViewModel = ViewModelProviders
                .of(activity as FragmentActivity, InjectUtils.provideMainViewModel(baseContext()))
                .get(MainViewModel::class.java)

        repeatMode = requireViewById(R.id.repeat_mode)
        preSong = requireViewById(R.id.pre_song)
        centerControl = requireViewById(R.id.center_control)
        nextSong = requireViewById(R.id.next_song)
        songList = requireViewById(R.id.song_list)

        favorite = requireViewById(R.id.playing_favorite)
        download = requireViewById(R.id.playing_download)
        share = requireViewById(R.id.playing_share)
        comment = requireViewById(R.id.playing_comment)

        seekTimePlayed = requireViewById(R.id.seek_current_time)
        seekTotalTime = requireViewById(R.id.seek_all_time)
        seekBar = requireViewById(R.id.player_seek_bar)

        seekBar.isIndeterminate = false
        seekBar.max = 1000
        seekBar.progress = 1

        seekBar.setOnSeekBarChangeListener(this)
        repeatMode.setOnClickListener(this)
        preSong.setOnClickListener(this)
        centerControl.setOnClickListener(this)
        nextSong.setOnClickListener(this)
        songList.setOnClickListener(this)
        favorite.setOnClickListener(this)
        download.setOnClickListener(this)
        share.setOnClickListener(this)
        comment.setOnClickListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        playingViewModel.playingState.observe(this, Observer {
            if (it == PlayState.PLAYING) {
                centerControl.setImageResource(R.drawable.default_player_btn_pause_selector)
            } else {
                centerControl.setImageResource(R.drawable.default_player_btn_play_selector)
            }
        })

        playingViewModel.repeatMode.observe(this, Observer { mode ->
            @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
            when (mode) {
                RepeatMode.ALL -> {
                    repeatMode.setImageResource(R.drawable.player_btn_repeat_all)
                }
                RepeatMode.ONE -> {
                    repeatMode.setImageResource(R.drawable.player_btn_repeat_once)
                }
                RepeatMode.SHUFFLE -> {
                    repeatMode.setImageResource(R.drawable.player_btn_random_normal)
                }
                RepeatMode.EMPTY -> {
                }
            }
        })
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        seekTimePlayed.text = progress.toLong().ms2Minute()
        val i = 100L / 1000
        if (fromUser) {
            seekTimePlayed.text = i.ms2Minute()
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.repeat_mode -> {
                playingViewModel.changeRepeatMode()
            }
            R.id.pre_song -> {
                mainViewModel.skipToPrevious()
            }
            R.id.center_control -> {
                playingViewModel.mediaMetadata.value?.let {
                    mainViewModel.playMediaId(it.id)
                }
            }
            R.id.next_song -> {
                mainViewModel.skipToNext()
            }
            R.id.playing_favorite -> {
//                baseContext().contentResolver.insert(LOVE_SONG_CONTENT_URI, values)
            }
        }
    }
}