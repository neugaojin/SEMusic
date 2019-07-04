package com.se.music.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.se.music.R
import com.se.music.databinding.FragmentBaseBinding
import com.se.music.utils.setTransparentForWindow

/**
 * Creator：gaojin
 * date：2017/11/6 下午8:32
 */
abstract class ToolBarActivity : AppCompatActivity() {
    private lateinit var mBinding: FragmentBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = LayoutInflater.from(this)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_base, null, false)
        val rootView = mBinding.root as LinearLayout
        rootView.addView(createContentView(inflater, rootView))
        setContentView(rootView)
        setTransparentForWindow(this)
        setSupportActionBar(mBinding.includeToolBar.baseToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    protected abstract fun createContentView(inflater: LayoutInflater, rootView: ViewGroup): View

    override fun setTitle(title: CharSequence) {
        mBinding.includeToolBar.toolbarTitle.text = title
    }

    fun setStatusBarColor(@ColorRes color: Int) {
        mBinding.fakeStatusBar.setBackgroundResource(color)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }
}