package com.se.music.widget.loading.drawable

import android.animation.ValueAnimator
import android.graphics.*
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import com.se.music.widget.loading.animation.AnimationUtils

/**
 *Author: gaojin
 *Time: 2019-12-19 18:34
 */

abstract class LoadDrawable : Drawable(), ValueAnimator.AnimatorUpdateListener, Animatable, Drawable.Callback {

    var scale = 1f
        set(value) {
            scaleX = value
            scaleY = value
            field = value
        }
    var scaleX = 1f
    var scaleY = 1f
    var pivotX = 0f
    var pivotY = 0f
    var animationDelay = 0
    var rotateX = 0
    var rotateY = 0
    var translateX = 0
    var translateY = 0
    var translateXPercentage = 0f
    var translateYPercentage = 0f
    var mCamera = Camera()
    var mMatrix = Matrix()
    var drawBounds = Rect()

    var animator: ValueAnimator? = null

    abstract fun getColor(): Int

    abstract fun setColor(color: Int)

    open fun onCreateAnimation(): ValueAnimator? {
        return null
    }

    override fun start() {
        if (AnimationUtils.isStarted(animator)) {
            return
        }

        animator = obtainAnimation()
        if (animator == null) {
            return
        }

        AnimationUtils.start(animator)
        invalidateSelf()
    }

    override fun stop() {
        if (AnimationUtils.isStarted(animator)) {
            animator!!.removeAllUpdateListeners()
            animator!!.end()
            reset()
        }
    }

    override fun isRunning(): Boolean {
        return AnimationUtils.isRunning(animator)
    }

    open fun obtainAnimation(): ValueAnimator? {
        if (animator == null) {
            animator = onCreateAnimation()
        }
        if (animator != null) {
            animator!!.addUpdateListener(this)
            animator!!.startDelay = animationDelay.toLong()
        }
        return animator
    }


    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        setDrawBounds(bounds.left, bounds.top, bounds.right, bounds.bottom)
    }

    open fun setDrawBounds(left: Int, top: Int, right: Int, bottom: Int) {
        drawBounds = Rect(left, top, right, bottom)
        pivotX = drawBounds.centerX().toFloat()
        pivotY = drawBounds.centerY().toFloat()
    }

    override fun onAnimationUpdate(animation: ValueAnimator?) {
        val callback = callback
        callback?.invalidateDrawable(this)
    }

    override fun invalidateDrawable(p0: Drawable) {
        invalidateSelf()
    }

    override fun scheduleDrawable(who: Drawable, what: Runnable, `when`: Long) {}

    override fun unscheduleDrawable(who: Drawable, what: Runnable) {}

    override fun draw(canvas: Canvas) {
        val tx = if (translateX == 0) (bounds.width() * translateXPercentage).toInt() else translateX
        val ty = if (translateY == 0) (bounds.height() * translateYPercentage).toInt() else translateY
        canvas.translate(tx.toFloat(), ty.toFloat())
        canvas.scale(scaleX, scaleY, pivotX, pivotY)
        if (rotateX != 0 || rotateY != 0) {
            mCamera.save()
            mCamera.rotateX(rotateX.toFloat())
            mCamera.rotateY(rotateY.toFloat())
            mCamera.getMatrix(mMatrix)
            mMatrix.preTranslate(-pivotX, -pivotY)
            mMatrix.postTranslate(pivotX, pivotY)
            mCamera.restore()
            canvas.concat(mMatrix)
        }
        drawSelf(canvas)
    }

    open fun drawSelf(canvas: Canvas) {

    }

    override fun setAlpha(alpha: Int) {
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }

    private fun reset() {
        scale = 1f
        rotateX = 0
        rotateY = 0
        translateX = 0
        translateY = 0
        translateXPercentage = 0f
        translateYPercentage = 0f
    }
}