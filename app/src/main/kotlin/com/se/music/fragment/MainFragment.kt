package com.se.music.fragment

import android.os.Bundle
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.se.music.R
import com.se.music.adapter.MainFragmentAdapter
import com.se.music.base.BaseFragment

class MainFragment : BaseFragment(), ViewPager.OnPageChangeListener, View.OnClickListener {

    companion object {
        const val TAG = "MainFragment"
        const val MINE = 0
        const val MUSIC = 1
        const val FIND = 2
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    private lateinit var mineView: TextView
    private lateinit var musicRoomView: TextView
    private lateinit var findView: TextView

    private lateinit var mViewPager: ViewPager
    private lateinit var mAdapter: MainFragmentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_app_main, container, false)
        mViewPager = view.findViewById(R.id.view_pager)
        mineView = view.findViewById(R.id.tool_bar_mine)
        musicRoomView = view.findViewById(R.id.tool_bar_music_room)
        findView = view.findViewById(R.id.tool_bar_find)

        mineView.setOnClickListener(this)
        musicRoomView.setOnClickListener(this)
        findView.setOnClickListener(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewPager.offscreenPageLimit = 2
        mAdapter = MainFragmentAdapter(activity?.supportFragmentManager!!)
        mViewPager.adapter = mAdapter
        mViewPager.currentItem = 1
        setTitleStyle(MUSIC)
        mViewPager.addOnPageChangeListener(this)
    }

    override fun onClick(v: View) {
        val id = v.id
        when (id) {
            R.id.tool_bar_mine -> {
                mViewPager.currentItem = 0
                setTitleStyle(0)
            }
            R.id.tool_bar_music_room -> {
                mViewPager.currentItem = 1
                setTitleStyle(1)
            }
            R.id.tool_bar_find -> {
                mViewPager.currentItem = 2
                setTitleStyle(2)
            }
        }
    }

    private fun setTitleStyle(position: Int) {
        when (position) {
            MINE -> {
                mineView.textSize = 19f
                mineView.setTextColor(ContextCompat.getColor(context!!, R.color.white))

                musicRoomView.textSize = 18f
                musicRoomView.setTextColor(ContextCompat.getColor(context!!, R.color.light_gray))

                findView.textSize = 18f
                findView.setTextColor(ContextCompat.getColor(context!!, R.color.light_gray))
            }
            MUSIC -> {
                musicRoomView.textSize = 19f
                musicRoomView.setTextColor(ContextCompat.getColor(context!!, R.color.white))

                mineView.textSize = 18f
                mineView.setTextColor(ContextCompat.getColor(context!!, R.color.light_gray))

                findView.textSize = 18f
                findView.setTextColor(ContextCompat.getColor(context!!, R.color.light_gray))
            }
            FIND -> {
                findView.textSize = 19f
                findView.setTextColor(ContextCompat.getColor(context!!, R.color.white))

                mineView.textSize = 18f
                mineView.setTextColor(ContextCompat.getColor(context!!, R.color.light_gray))

                musicRoomView.textSize = 18f
                musicRoomView.setTextColor(ContextCompat.getColor(context!!, R.color.light_gray))
            }
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        setTitleStyle(position)
    }
}