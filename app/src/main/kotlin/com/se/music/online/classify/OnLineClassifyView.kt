package com.se.music.online.classify

import android.view.LayoutInflater
import android.view.View
import com.se.music.R
import com.se.music.base.mvp.BaseView
import com.se.music.base.mvp.MvpPresenter
import com.se.music.fragment.MainFragment
import com.se.music.fragment.OnLineSingerFragment
import com.se.music.utils.startFragment

/**
 * Created by gaojin on 2018/3/6.
 */
class OnLineClassifyView(presenter: MvpPresenter, viewId: Int) : BaseView(presenter, viewId), View.OnClickListener {

    init {
        initView()
    }

    override fun createView(): View {
        val rootView = LayoutInflater.from(getContext()).inflate(R.layout.online_classify_block, null)
        val singer: View = rootView.findViewById(R.id.classify_singer)
        val rank: View = rootView.findViewById(R.id.classify_rank)
        val radio: View = rootView.findViewById(R.id.classify_radio)
        val list: View = rootView.findViewById(R.id.classify_song_list)
        val mv: View = rootView.findViewById(R.id.classify_mv)
        val album: View = rootView.findViewById(R.id.classify_album)

        singer.setOnClickListener(this)
        rank.setOnClickListener(this)
        radio.setOnClickListener(this)
        list.setOnClickListener(this)
        mv.setOnClickListener(this)
        album.setOnClickListener(this)
        return rootView
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.classify_singer) {
            startFragment(getPage(), OnLineSingerFragment.newInstance(), MainFragment.TAG)
        }
    }
}