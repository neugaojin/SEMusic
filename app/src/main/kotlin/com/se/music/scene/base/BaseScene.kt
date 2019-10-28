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
 *Time: 2019-10-21 15:16
 */

abstract class BaseScene : GroupScene() {

    private lateinit var mBinding: FragmentBaseBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): ViewGroup {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_base, null, false)
        (mBinding.root as ViewGroup).addView(createContentView(inflater, container))
        (activity as AppCompatActivity).setSupportActionBar(mBinding.baseToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowTitleEnabled(false)
        return mBinding.root as ViewGroup
    }

    fun setTitle(title: String?) {
        mBinding.toolbarTitle.text = title
    }

    abstract fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View
}