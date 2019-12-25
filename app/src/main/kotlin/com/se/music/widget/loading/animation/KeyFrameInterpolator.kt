package com.se.music.widget.loading.animation

import android.animation.TimeInterpolator
import android.view.animation.Interpolator

/**
 *Author: gaojin
 *Time: 2019-12-22 16:38
 */

class KeyFrameInterpolator(val interpolator: TimeInterpolator,
                           var fractions: FloatArray) : Interpolator {


    override fun getInterpolation(input: Float): Float {
        if (fractions.size > 1) {
            for (i in 0 until fractions.size - 1) {
                val start = fractions[i]
                val end = fractions[i + 1]
                val duration = end - start
                if (input in start..end) {
                    val inInput = (input - start) / duration
                    return start + (interpolator.getInterpolation(inInput) * duration)
                }
            }
        }
        return interpolator.getInterpolation(input)
    }
}