package com.se.music.widget.loading.animation

import android.animation.Animator
import android.animation.ValueAnimator
import com.se.music.widget.loading.drawable.Dot
import com.se.music.widget.loading.drawable.LoadDrawable

/**
 *Author: gaojin
 *Time: 2019-12-20 10:51
 */

object AnimationUtils {
    fun start(animator: Animator?) {
        if (animator != null && !animator.isStarted) {
            animator.start()
        }
    }

    fun start(sprites: Array<Dot>) {
        sprites.forEach {
            it.start()
        }
    }

    fun stop(sprites: Array<Dot>) {
        sprites.forEach { it.stop() }
    }

    fun isRunning(vararg sprites: LoadDrawable): Boolean {
        for (sprite in sprites) {
            if (sprite.isRunning) {
                return true
            }
        }
        return false
    }

    fun isRunning(animator: ValueAnimator?): Boolean {
        return animator != null && animator.isRunning
    }

    fun isStarted(animator: ValueAnimator?): Boolean {
        return animator != null && animator.isStarted
    }
}