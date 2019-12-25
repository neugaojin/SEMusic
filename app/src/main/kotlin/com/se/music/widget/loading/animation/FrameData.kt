package com.se.music.widget.loading.animation

import android.util.Property

/**
 *Author: gaojin
 *Time: 2019-12-22 16:58
 */

sealed class FrameData<T>(var fractions: FloatArray, var property: Property<*, *>, var values: Array<T>)

class IntFrameData(fractions: FloatArray, property: Property<*, *>, values: Array<Int>) : FrameData<Int>(fractions, property, values)
class FloatFrameData(fractions: FloatArray, property: Property<*, *>, values: Array<Float>) : FrameData<Float>(fractions, property, values)
