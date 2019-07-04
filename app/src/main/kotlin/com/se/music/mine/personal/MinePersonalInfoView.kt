package com.se.music.mine.personal

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import com.se.music.R
import com.se.music.base.mvp.BaseView
import com.se.music.base.mvp.MvpPresenter

/**
 * Author: gaojin
 * Time: 2018/5/6 下午7:32
 */
class MinePersonalInfoView(presenter: MvpPresenter, viewId: Int, header: View) : BaseView(presenter, viewId) {

    init {
        initView(header)
    }

    @SuppressLint("InflateParams")
    override fun createView(): View {
        return LayoutInflater.from(getContext()).inflate(R.layout.mine_head_layout, null)
    }
}