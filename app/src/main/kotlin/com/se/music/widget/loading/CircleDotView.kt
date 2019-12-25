package com.se.music.widget.loading

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import com.se.music.R
import com.se.music.widget.loading.drawable.Circle
import com.se.music.widget.loading.drawable.LoadDrawable

/**
 *Author: gaojin
 *Time: 2019-12-22 17:07
 */

class CircleDotView @JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ProgressBar(context, attrs, defStyleAttr) {

    private var mColor = 0
    private var dotRadius = 0
    private var loadRadius = 0
    private lateinit var loadDrawable: LoadDrawable

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.SpinKitViewStyle, defStyleAttr, 0)
        mColor = a.getColor(R.styleable.SpinKitViewStyle_dot_color, Color.WHITE)
        dotRadius = a.getDimensionPixelOffset(R.styleable.SpinKitViewStyle_dot_radius, context.resources.getDimensionPixelOffset(R.dimen.loading_dot_radius))
        loadRadius = a.getDimensionPixelOffset(R.styleable.SpinKitViewStyle_load_radius, context.resources.getDimensionPixelOffset(R.dimen.loading_load_radius))
        a.recycle()
        val sprite = Circle().apply {
            setDotRadius(dotRadius)
            setLoadRadius(loadRadius)
            setColor(mColor)
        }
        setIndeterminateDrawable(sprite)
        isIndeterminate = true
    }

    override fun setIndeterminateDrawable(d: Drawable?) {
        require(d is LoadDrawable) { "this d must be instanceof Sprite" }
        indeterminateDrawable = d
    }

    private fun setIndeterminateDrawable(d: LoadDrawable) {
        super.setIndeterminateDrawable(d)
        loadDrawable = d
        if (loadDrawable.getColor() == 0) {
            loadDrawable.setColor(mColor)
        }
        onSizeChanged(width, height, width, height)
        if (visibility == View.VISIBLE) {
            loadDrawable.start()
        }
    }

    override fun getIndeterminateDrawable(): LoadDrawable? {
        return loadDrawable
    }

    fun setColor(color: Int) {
        mColor = color
        loadDrawable.setColor(color)
        invalidate()
    }

    override fun unscheduleDrawable(who: Drawable?) {
        super.unscheduleDrawable(who)
        if (who is LoadDrawable) {
            who.stop()
        }
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        if (hasWindowFocus) {
            if (visibility == View.VISIBLE) {
                loadDrawable.start()
            }
        }
    }

    override fun onScreenStateChanged(screenState: Int) {
        super.onScreenStateChanged(screenState)
        if (screenState == View.SCREEN_STATE_OFF) {
            loadDrawable.stop()
        }
    }
}