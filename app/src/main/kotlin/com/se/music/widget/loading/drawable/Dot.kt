package com.se.music.widget.loading.drawable

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.se.music.widget.loading.animation.SpriteAnimatorBuilder

/**
 *Author: gaojin
 *Time: 2019-12-19 19:34
 */

class Dot(private val radius: Int) : LoadDrawable() {

    private var mPaint = Paint()
    private var mUseColor = 0
    private var mBaseColor = 0

    init {
        setColor(Color.WHITE)
        mPaint.isAntiAlias = true
        mPaint.color = mUseColor
        scale = 0f
    }

    override fun getColor(): Int {
        return mBaseColor
    }

    override fun setColor(color: Int) {
        mBaseColor = color
        updateUseColor()
    }

    override fun drawSelf(canvas: Canvas) {
        mPaint.color = mUseColor
        canvas.drawCircle(drawBounds.centerX().toFloat(), drawBounds.centerY().toFloat(), radius.toFloat(), mPaint)
    }

    override fun setAlpha(alpha: Int) {
        super.setAlpha(alpha)
        updateUseColor()
    }

    override fun onCreateAnimation(): ValueAnimator {
        val fractions = floatArrayOf(0f, 0.5f, 1f)
        return SpriteAnimatorBuilder(this)
                .scale(fractions, arrayOf(0f, 1f, 0f))
                .duration(1200)
                .easeInOut(fractions)
                .build()
    }

    private fun updateUseColor() {
        var alpha = alpha
        alpha += alpha shr 7
        val baseAlpha = mBaseColor ushr 24
        val useAlpha = baseAlpha * alpha shr 8
        mUseColor = mBaseColor shl 8 ushr 8 or (useAlpha shl 24)
    }
}