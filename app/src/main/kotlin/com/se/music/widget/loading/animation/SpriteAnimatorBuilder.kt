package com.se.music.widget.loading.animation

import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.util.FloatProperty
import android.util.Property
import android.view.animation.Animation
import android.view.animation.Interpolator
import androidx.core.view.animation.PathInterpolatorCompat
import com.se.music.widget.loading.drawable.LoadDrawable
import java.util.*

/**
 *Author: gaojin
 *Time: 2019-12-22 16:52
 */

class SpriteAnimatorBuilder(var sprite: LoadDrawable) {

    private var interpolator: Interpolator? = null
    private var duration: Long = 2000
    private val fds: MutableMap<String, FrameData<*>> = HashMap()

    fun scale(fractions: FloatArray, scale: Array<Float>): SpriteAnimatorBuilder {
        holder(fractions, scale)
        return this
    }

    private fun holder(fractions: FloatArray, values: Array<Float>) {
        ensurePair(fractions.size, values.size)
        fds[SCALE.name] = FloatFrameData(fractions, SCALE, values)
    }

    private fun ensurePair(fractionsLength: Int, valuesLength: Int) {
        check(fractionsLength == valuesLength) {
            String.format(
                    Locale.getDefault(),
                    "The fractions.length must equal values.length, " +
                            "fraction.length[%d], values.length[%d]",
                    fractionsLength,
                    valuesLength)
        }
    }


    fun interpolator(interpolator: Interpolator?): SpriteAnimatorBuilder {
        this.interpolator = interpolator
        return this
    }

    fun easeInOut(fractions: FloatArray?): SpriteAnimatorBuilder {
        val interpolator = PathInterpolatorCompat.create(0.42f, 0f, 0.58f, 1f)
        val keyFrameInterpolator = KeyFrameInterpolator(interpolator, fractions!!)
        interpolator(keyFrameInterpolator)
        return this
    }


    fun duration(duration: Long): SpriteAnimatorBuilder {
        this.duration = duration
        return this
    }

    fun build(): ValueAnimator {
        val holders = arrayOfNulls<PropertyValuesHolder>(fds.size)
        var i = 0
        for ((_, data) in fds) {
            val keyframes = arrayOfNulls<Keyframe>(data.fractions.size)
            val fractions = data.fractions
            val startFrame = 0
            val startF = fractions[startFrame]
            for (j in startFrame until startFrame + data.values.size) {
                val key = j - startFrame
                val vk = j % data.values.size
                var fraction = fractions[vk] - startF
                if (fraction < 0) {
                    fraction += fractions[fractions.size - 1]
                }
                when (data) {
                    is IntFrameData -> {
                        keyframes[key] = Keyframe.ofInt(fraction, (data.values[vk] as Int?)!!)
                    }
                    is FloatFrameData -> {
                        keyframes[key] = Keyframe.ofFloat(fraction, (data.values[vk] as Float?)!!)
                    }
                }
            }
            holders[i] = PropertyValuesHolder.ofKeyframe(data.property, *keyframes)
            i++
        }
        val animator = ObjectAnimator.ofPropertyValuesHolder(sprite,
                *holders)
        animator.duration = duration
        val repeatCount = Animation.INFINITE
        animator.repeatCount = repeatCount
        animator.interpolator = interpolator
        return animator
    }

    private val SCALE: Property<LoadDrawable, Float> = object : FloatProperty<LoadDrawable>("scale") {
        override fun setValue(`object`: LoadDrawable, value: Float) {
            `object`.scale = value
        }

        override fun get(`object`: LoadDrawable): Float {
            return `object`.scale
        }
    }
}