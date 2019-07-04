package com.se.music.widget.lrc

import android.text.format.DateUtils
import java.util.*

/**
 *Author: gaojin
 *Time: 2018/10/18 下午11:51
 */
fun formatTime(milli: Long): String {
    val m = (milli / DateUtils.MINUTE_IN_MILLIS).toInt()
    val s = (milli / DateUtils.SECOND_IN_MILLIS % 60).toInt()
    val mm = String.format(Locale.getDefault(), "%02d", m)
    val ss = String.format(Locale.getDefault(), "%02d", s)
    return "$mm:$ss"
}