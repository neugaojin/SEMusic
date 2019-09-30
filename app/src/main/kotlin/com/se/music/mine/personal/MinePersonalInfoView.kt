package com.se.music.mine.personal

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import com.se.music.R

/**
 * Author: gaojin
 * Time: 2018/5/6 下午7:32
 */
class MinePersonalInfoView(presenter: com.se.router.mvp.MvpPresenter, viewId: Int, header: View) : com.se.router.mvp.BaseView(presenter, viewId) {

    init {
        initView(header)
    }

    @SuppressLint("InflateParams")
    override fun createView(): View {
        return LayoutInflater.from(getContext()).inflate(R.layout.mine_head_layout, null)
    }
}