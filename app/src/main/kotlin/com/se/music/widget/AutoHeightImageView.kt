package com.se.music.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

/**
 * Created by gaojin on 2017/12/31.
 */
class AutoHeightImageView : ImageView {

    private val ratioHelper: AspectRatioViewHelper

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        ratioHelper = AspectRatioViewHelper(this, attrs)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val outDimension = ratioHelper.calculateDimension(measuredWidth, measuredHeight)
        setMeasuredDimension(outDimension[0], outDimension[1])
    }
}