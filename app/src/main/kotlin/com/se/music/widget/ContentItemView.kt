package com.se.music.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.se.music.R

/**
 *Author: gaojin
 *Time: 2018/10/12 下午6:32
 */

class ContentItemView @JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr) {

    var headImg: ImageView
    var title: TextView
    var subTitle: TextView

    init {
        orientation = HORIZONTAL
        View.inflate(context, R.layout.view_content_item_view, this)
        headImg = findViewById(R.id.content_img)
        title = findViewById(R.id.content_item_title)
        subTitle = findViewById(R.id.content_item_sub_title)
    }
}