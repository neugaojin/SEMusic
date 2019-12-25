package com.se.music.widget.loading

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.se.music.R

/**
 *Author: gaojin
 *Time: 2019-12-23 20:50
 */

class LoadingView @JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.base_loading_view, this)
        background = context.resources.getDrawable(R.color.white)
    }
}