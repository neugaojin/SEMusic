package com.se.music.online.newsong

import android.view.View
import androidx.annotation.Keep
import com.se.music.base.mvp.BaseView
import com.se.music.base.mvp.MvpPresenter
import com.se.music.online.model.ExpressInfoModel

/**
 * Created by gaojin on 2018/3/7.
 * 新歌速递
 */
class OnLineNewSongView(presenter: MvpPresenter, viewId: Int) : BaseView(presenter, viewId) {

    private lateinit var expressView: NewSongExpressBlock

    override fun createView(): View {
        expressView = NewSongExpressBlock(getContext()!!)
        return expressView
    }

    @Keep
    fun onDataChanged(data: ExpressInfoModel) {
        expressView.dataChanged(data)
    }
}