package com.se.music.widget.loading.drawable

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import com.se.music.widget.loading.animation.AnimationUtils

/**
 *Author: gaojin
 *Time: 2019-12-19 19:39
 */
class Circle : LoadDrawable() {

    companion object {
        const val DEFAULT_LOADING_RADIUS = 100
        const val DEFAULT_DOT_RADIUS = 15
        const val DEFAULT_DOT_COUNT = 12
    }

    private var color = 0
    private var dotCount = DEFAULT_DOT_COUNT
    private var loadingRadius = DEFAULT_LOADING_RADIUS
    private var dotRadius = DEFAULT_DOT_RADIUS

    private var sprites = onCreateChild()

    init {
        sprites.forEach {
            it.callback = this
        }
    }

    override fun setColor(color: Int) {
        this.color = color
        sprites.forEach {
            it.setColor(color)
        }
    }

    fun setDotRadius(radius: Int) {
        this.dotRadius = radius
    }

    fun setLoadRadius(radius: Int) {
        this.loadingRadius = radius
    }

    override fun getColor(): Int {
        return color
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        drawChild(canvas)
    }

    private fun drawChild(canvas: Canvas) {
        sprites.forEachIndexed { index, dot ->
            val count = canvas.save()
            canvas.rotate((index * 360 / dotCount).toFloat(), pivotX, pivotY)
            dot.draw(canvas)
            canvas.restoreToCount(count)
        }
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        val clipBounds = Rect((pivotX - loadingRadius).toInt()
                , (pivotY - loadingRadius).toInt()
                , (pivotX + loadingRadius).toInt()
                , (pivotY + loadingRadius).toInt())

        val left = clipBounds.centerX() - dotRadius
        val right = clipBounds.centerX() + dotRadius
        sprites.forEach {
            it.setDrawBounds(left, clipBounds.top, right, clipBounds.top + dotRadius * 2)
        }
    }

    override fun start() {
        super.start()
        AnimationUtils.start(sprites)
    }

    override fun stop() {
        super.stop()
        AnimationUtils.stop(sprites)
    }

    override fun isRunning(): Boolean {
        return AnimationUtils.isRunning(*sprites) || super.isRunning()
    }

    private fun onCreateChild(): Array<Dot> {
        val dots = Array(dotCount) { Dot(dotRadius) }
        dots.forEachIndexed { index, dot ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                dot.animationDelay = 1200 / dotCount * index
            } else {
                dot.animationDelay = 1200 / dotCount * index - 1200
            }
        }
        return dots
    }
}