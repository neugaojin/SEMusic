package com.se.music.utils

import android.graphics.Bitmap
import com.se.music.utils.manager.BlurManager

/**
 *Author: gaojin
 *Time: 2018/8/19 下午4:07
 */

fun blurBitmap(bitmap: Bitmap, process: Int): Bitmap? {
    val blurBitmap: Bitmap?
    val manager = BlurManager(bitmap)
    blurBitmap = manager.process(process)
    return blurBitmap
}