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
import androidx.fragment.app.FragmentManager
import androidx.loader.app.LoaderManager
import com.se.music.R

/**
 * Created by gaojin on 2018/2/28.
 */
abstract class BasePageFragment : BaseFragment() {
    protected lateinit var mToolBar: Toolbar
    private lateinit var mTitle: TextView
    private lateinit var statusBar: View
    protected lateinit var fm: FragmentManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val linearLayout = inflater.inflate(R.layout.fragment_base, container, false) as LinearLayout?
        linearLayout!!.addView(createContentView(inflater, container))

        mToolBar = linearLayout.findViewById(R.id.base_toolbar)
        mTitle = linearLayout.findViewById(R.id.toolbar_title)
        statusBar = linearLayout.findViewById(R.id.fake_status_bar)
        mLoaderManager = LoaderManager.getInstance(this)
        fm = fragmentManager!!

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(mToolBar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowTitleEnabled(false)
        return linearLayout
    }

    fun setTitle(title: String) {
        mTitle.text = title
    }

    fun setStatusBarColor(@ColorRes color: Int) {
        statusBar.setBackgroundResource(color)
    }

    fun hideStatStatusBar() {
        statusBar.visibility = View.GONE
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