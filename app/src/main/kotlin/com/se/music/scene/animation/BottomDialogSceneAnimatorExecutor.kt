package com.se.music.scene.animation

import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.Color
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.annotation.NonNull
import com.bytedance.scene.Scene
import com.bytedance.scene.animation.AnimationInfo
import com.bytedance.scene.animation.NavigationAnimatorExecutor
import com.se.music.R
import com.se.music.scene.base.DialogScene

/**
 *Author: gaojin
 *Time: 2020/4/29 4:39 PM
 */

class BottomDialogSceneAnimatorExecutor : NavigationAnimatorExecutor() {
    override fun disableConfigAnimationDuration(): Boolean {
        return true
    }

    override fun isSupport(from: Class<out Scene>, to: Class<out Scene>): Boolean {
        return true
    }

    @NonNull
    override fun onPushAnimator(from: AnimationInfo?, to: AnimationInfo): Animator {
        val toView = to.mSceneView
        val contentView = toView.findViewById<View>(R.id.scene_dialog_content_view)

        val valueAnimator = ValueAnimator.ofArgb(Color.TRANSPARENT, Color.parseColor("#80000000"))
        valueAnimator.addUpdateListener { animation ->
            val fraction = animation.animatedFraction
            toView.setBackgroundColor(animation.animatedValue as Int)
            contentView?.let {
                it.translationY = (1f - fraction) * it.height
            }
        }
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.duration = ANIMATION_DURATION
        return valueAnimator
    }

    @NonNull
    override fun onPopAnimator(fromInfo: AnimationInfo, toInfo: AnimationInfo?): Animator {
        val fromView = fromInfo.mSceneView
        val contentView = fromView.findViewById<View>(R.id.scene_dialog_content_view)

        val valueAnimator = ValueAnimator.ofArgb(Color.parseColor("#80000000"), Color.TRANSPARENT)
        valueAnimator.addUpdateListener { animation ->
            val fraction = animation.animatedFraction
            fromView.setBackgroundColor(animation.animatedValue as Int)
            contentView?.let {
                it.translationY = fraction * it.height
            }
        }
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.duration = ANIMATION_DURATION
        return valueAnimator
    }

    companion object {
        private const val ANIMATION_DURATION: Long = 150
    }
}