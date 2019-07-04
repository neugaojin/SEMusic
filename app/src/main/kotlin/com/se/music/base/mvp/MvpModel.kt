package com.se.music.base.mvp

/**
 * Created by gaojin on 2018/2/4.
 * MVP - Model
 */
interface MvpModel {
    fun setPresenter(presenter: MvpPresenter)

    fun <D : Any> onDataChanged(data: D)

    fun load()

    fun getId(): Int
}