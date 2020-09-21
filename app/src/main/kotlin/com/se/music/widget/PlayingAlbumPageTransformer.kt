package com.se.music.widget

import android.view.View
import androidx.viewpager.widget.ViewPager

/**
 *Author: gaojin
 *Time: 2018/10/8 下午7:00
 */

class PlayingAlbumPageTransformer : ViewPager.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        val pageWidth = page.width
        if (page.tag is String && page.tag.toString() == "") {
            when {
                position < 0 -> { // [-1,0)
                    page.alpha = 1 + position
                    page.translationX = pageWidth * -position
                }
                position <= 1 -> { // (0,1]
                    page.alpha = 1 - position
                    page.translationX = pageWidth * -position
                }
            }
        }
    }
}