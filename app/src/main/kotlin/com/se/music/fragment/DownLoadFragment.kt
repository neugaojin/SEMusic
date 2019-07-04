package com.se.music.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.se.music.R
import com.se.music.base.BasePageFragment

/**
 * Creator：gaojin
 * date：2017/11/6 下午8:44
 */
class DownLoadFragment : BasePageFragment() {
    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        val content = LayoutInflater.from(context).inflate(R.layout.activity_download, container, false)
        mTabLayout = content.findViewById(R.id.local_tab_layout)
        mViewPager = content.findViewById(R.id.local_view_pager)
        return content
    }

    val names = arrayOf("已下载", "正在下载")

    private var mAdapter: FragmentAdapter? = null

    private var mTabLayout: TabLayout? = null

    private var mViewPager: ViewPager? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setTitle(context!!.getString(R.string.down_music_title))
        mAdapter = FragmentAdapter(fm)
        mViewPager!!.adapter = mAdapter
        mTabLayout!!.setupWithViewPager(mViewPager)
    }

    inner class FragmentAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return if (position == 0) {
                LocalSongFragment.newInstance()
            } else {
                LocalSongFragment.newInstance()
            }
        }

        override fun getCount(): Int {
            return 2
        }

        // 此方法用来显示tab上的名字
        override fun getPageTitle(position: Int): CharSequence {
            return names[position % 2]
        }
    }

    companion object {
        fun newInstance(): DownLoadFragment {
            return DownLoadFragment()
        }
    }
}