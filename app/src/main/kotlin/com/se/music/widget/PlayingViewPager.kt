package com.se.music.widget

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager

/**
 *Author: gaojin
 *Time: 2018/10/10 下午8:36
 */

class PlayingViewPager : ViewPager {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    /**
     * 修改Child绘制顺序
     */
    override fun getChildDrawingOrder(childCount: Int, i: Int): Int {
        return if (childCount >= 3) {
            when (i) {
                childCount - 1 -> childCount - 2
                childCount - 2 -> childCount - 1
                else -> i
            }
        } else {
            super.getChildDrawingOrder(childCount, i)
        }
    }
}