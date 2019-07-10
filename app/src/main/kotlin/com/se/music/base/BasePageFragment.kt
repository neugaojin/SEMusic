package com.se.music.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.loader.app.LoaderManager
import com.se.music.R
import com.se.music.databinding.FragmentBaseBinding

/**
 * Created by gaojin on 2018/2/28.
 */
abstract class BasePageFragment : BaseFragment() {
    protected lateinit var fm: FragmentManager

    private lateinit var mBinding: FragmentBaseBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_base, null, false)
        (mBinding.root as ViewGroup).addView(createContentView(inflater, container))
        mLoaderManager = LoaderManager.getInstance(this)
        fm = fragmentManager!!
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(mBinding.baseToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowTitleEnabled(false)
        return mBinding.root
    }

    fun setTitle(title: String) {
        mBinding.toolbarTitle.text = title
    }

    fun setStatusBarColor(@ColorRes color: Int) {
        mBinding.fakeStatusBar.setBackgroundResource(color)
    }

    fun hideStatStatusBar() {
        mBinding.fakeStatusBar.visibility = View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                fm.popBackStack()
            }
        }
        return true
    }

    abstract fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View
}