package com.se.music.scene.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bytedance.scene.group.GroupScene
import com.se.music.R
import com.se.music.databinding.FragmentBaseBinding

/**
 *Author: gaojin
 *Time: 2019-12-08 21:56
 * 带有Se主题Title的Scene
 */

abstract class SeCompatScene : GroupScene() {

    private lateinit var mBinding: FragmentBaseBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): ViewGroup {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_base, null, false)
        (mBinding.root as ViewGroup).addView(createContentView(inflater, container, savedInstanceState))
        (activity as AppCompatActivity).setSupportActionBar(mBinding.baseToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowTitleEnabled(false)

        mBinding.baseToolbar.setNavigationOnClickListener {
            requireNavigationScene().pop()
        }
        return mBinding.root as ViewGroup
    }

    fun setTitle(title: String) {
        mBinding.toolbarTitle.text = title
    }

    abstract fun createContentView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View
}