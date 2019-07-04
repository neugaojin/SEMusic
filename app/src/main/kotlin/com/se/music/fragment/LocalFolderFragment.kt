package com.se.music.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.se.music.R
import com.se.music.base.BaseFragment

/**
 *Author: gaojin
 *Time: 2018/7/10 上午12:03
 */

class LocalFolderFragment : BaseFragment() {

    companion object {
        const val url = "https://y.gtimg.cn/music/photo_new/T001R300x300M0000025NhlN2yWrP4.jpg"
        const val url1 = "https://y.gtimg.cn/music//T001R300x300M0000025NhlN2yWrP4.jpg"
        fun newInstance(): LocalFolderFragment {
            return LocalFolderFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_local_folder, container, false)
    }
}