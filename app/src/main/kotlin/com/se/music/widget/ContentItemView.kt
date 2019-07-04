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

class ContentItemView : LinearLayout {
    lateinit var headImg: ImageView
    lateinit var title: TextView
    lateinit var subTitle: TextView

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        orientation = LinearLayout.HORIZONTAL
        View.inflate(context, R.layout.view_content_item_view, this)
        headImg = findViewById(R.id.content_img)
        title = findViewById(R.id.content_item_title)
        subTitle = findViewById(R.id.content_item_sub_title)
    }
}