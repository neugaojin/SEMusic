package com.se.router.mvp

import android.view.View

/**
 * Created by gaojin on 2018/2/4.
 * MVP - View 接口定义
 */
interface MvpView {
    fun setPresenter(presenter: MvpPresenter)

    fun <D : Any> onDataChanged(data: D)

    fun <D : Any> onDataChanged(data: D, view: View)

    fun getView(): View

    fun getId(): Int
}