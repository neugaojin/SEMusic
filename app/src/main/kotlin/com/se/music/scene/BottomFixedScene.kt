package com.se.music.scene

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bytedance.scene.Scene
import com.bytedance.scene.animation.animatorexecutor.DialogSceneAnimatorExecutor
import com.bytedance.scene.interfaces.PushOptions
import com.se.music.R
import com.se.music.activity.PlayingActivity
import com.se.music.scene.animation.BottomDialogSceneAnimatorExecutor
import com.se.music.scene.base.DialogScene
import com.se.music.scene.local.LocalMainScene
import com.se.music.scene.sub.BottomListScene
import com.se.music.service.MusicPlayer
import com.se.music.support.utils.getLargeImageUrl
import com.se.music.support.utils.loadUrl
import com.se.music.uamp.InjectUtils
import com.se.music.uamp.MainActivityViewModel
import com.se.music.uamp.NowPlayingViewModel
import com.se.service.library.PlayState

/**
 *Author: gaojin
 *Time: 2019-10-20 21:26
 */

class BottomFixedScene : Scene(), View.OnClickListener {

    companion object {
        const val TAG = "BottomFixedScene"
    }

    private lateinit var album: ImageView
    private lateinit var playBarSongName: TextView
    private lateinit var playBarSinger: TextView
    private lateinit var playList: ImageView
    private lateinit var control: ImageView
    private lateinit var playNext: ImageView
    private lateinit var mLogan: TextView
    private lateinit var circleAnim: ObjectAnimator
    private lateinit var playingViewModel: NowPlayingViewModel
    private lateinit var mainViewModel: MainActivityViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.view_quick_controls, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        album = view.findViewById(R.id.play_bar_img)
        playBarSongName = view.findViewById(R.id.play_bar_song_name)
        playBarSinger = view.findViewById(R.id.play_bar_singer)
        playList = view.findViewById(R.id.play_list)
        control = view.findViewById(R.id.control)
        playNext = view.findViewById(R.id.play_next)
        mLogan = view.findViewById(R.id.music_logan)

        val albumCenterPoint = resources.getDimension(R.dimen.bottom_fragment_album_size) / 2
        album.pivotX = albumCenterPoint
        album.pivotY = albumCenterPoint

        circleAnim = ObjectAnimator.ofFloat(album, "rotation", 0f, 360f).apply {
            interpolator = LinearInterpolator()
            repeatCount = -1
            duration = 12000
            start()
        }

        view.setOnClickListener(this)
        view.elevation = 100f
        playList.setOnClickListener(this)
        control.setOnClickListener(this)
        playNext.setOnClickListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        playingViewModel = ViewModelProviders
                .of(activity as FragmentActivity, InjectUtils.provideNowPlayingViewModel(sceneContext!!))
                .get(NowPlayingViewModel::class.java)

        mainViewModel = ViewModelProviders
                .of(activity as FragmentActivity, InjectUtils.provideMainActivityViewModel(sceneContext!!))
                .get(MainActivityViewModel::class.java)

        playingViewModel.playingState.observe(this, Observer {
            if (it == PlayState.PLAYING) {
                control.setImageResource(R.drawable.playbar_btn_pause)
                circleAnim.resume()
            } else {
                control.setImageResource(R.drawable.playbar_btn_play)
                circleAnim.pause()
            }
        })

        playingViewModel.mediaMetadata.observe(this, Observer {
            if (it.title.isNullOrEmpty() || it.subtitle.isNullOrEmpty()) {
                playBarSongName.visibility = View.GONE
                playBarSinger.visibility = View.GONE
                album.visibility = View.GONE
                mLogan.visibility = View.VISIBLE
            } else {
                playBarSongName.visibility = View.VISIBLE
                playBarSinger.visibility = View.VISIBLE
                album.visibility = View.VISIBLE
                mLogan.visibility = View.GONE
                playBarSongName.text = it.title
                playBarSinger.text = it.subtitle
            }
        })
        album.loadUrl(MusicPlayer.getAlbumPic().getLargeImageUrl(), R.drawable.player_albumcover_default)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.play_list -> {
                DialogScene.show(requireNavigationScene(), BottomListScene::class.java)
            }
            R.id.control -> {
                playingViewModel.mediaMetadata.value?.let {
                    mainViewModel.playMediaId(it.id)
                }
            }
            R.id.play_next -> {
                mainViewModel.skipToNext()
            }
            else -> {
                val intent = Intent(activity, PlayingActivity::class.java)
                navigationScene?.startActivity(intent)
                activity!!.overridePendingTransition(R.anim.push_down_in, R.anim.push_up_out)
            }
        }
    }
}