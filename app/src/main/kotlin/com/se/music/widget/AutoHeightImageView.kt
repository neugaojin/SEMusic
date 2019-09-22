package com.se.music.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

/**
 * Created by gaojin on 2017/12/31.
 */
class AutoHeightImageView @JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ImageView(context, attrs, defStyleAttr) {

    private val ratioHelper: AspectRatioViewHelper = AspectRatioViewHelper(this, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val outDimension = ratioHelper.calculateDimension(measuredWidth, measuredHeight)
        setMeasuredDimension(outDimension[0], outDimension[1])
    }
}