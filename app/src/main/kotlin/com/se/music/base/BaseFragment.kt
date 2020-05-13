package com.se.music.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import com.se.music.base.listener.MusicStateListener

/**
 * Created by gaojin on 2018/3/6.
 */
open class BaseFragment : Fragment(), MusicStateListener {

    lateinit var mLoaderManager: LoaderManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLoaderManager = LoaderManager.getInstance(this)
    }

    override fun updateLrc() {
    }

    override fun musciChanged() {
    }

    override fun updatePlayInfo() {
    }

    override fun updateTime() {
    }

    override fun reloadAdapter() {
    }
}