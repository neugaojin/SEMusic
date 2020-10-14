package com.se.music.scene.hall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bytedance.scene.Scene
import com.se.music.R
import com.se.music.base.scene.baseContext
import com.se.music.base.scene.pushWithAnim
import com.se.music.mvvm.SingerListScene

/**
 *Author: gaojin
 *Time: 2019-10-25 15:58
 */

class COTScene : Scene() {

    private val clickListener = View.OnClickListener {
        when (it?.id) {
            R.id.classify_singer -> {
                pushWithAnim(SingerListScene::class.java)
            }
            else -> {

            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {
        val rootView = LayoutInflater.from(baseContext()).inflate(R.layout.online_classify_block, null)
        val singer: View = rootView.findViewById(R.id.classify_singer)
        val rank: View = rootView.findViewById(R.id.classify_rank)
        val radio: View = rootView.findViewById(R.id.classify_radio)
        val list: View = rootView.findViewById(R.id.classify_song_list)
        val mv: View = rootView.findViewById(R.id.classify_mv)
        val album: View = rootView.findViewById(R.id.classify_album)

        singer.setOnClickListener(clickListener)
        rank.setOnClickListener(clickListener)
        radio.setOnClickListener(clickListener)
        list.setOnClickListener(clickListener)
        mv.setOnClickListener(clickListener)
        album.setOnClickListener(clickListener)
        return rootView
    }
}