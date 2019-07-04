package com.se.music.widget.lrc

import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.text.TextUtils
import android.text.format.DateUtils
import java.io.File
import java.util.*
import java.util.regex.Pattern

/**
 *Author: gaojin
 *Time: 2018/10/18 下午11:19
 */

class LrcEntry(val time: Long, val text: String) : Comparable<LrcEntry> {

    companion object {
        const val GRAVITY_LEFT = 1
        const val GRAVITY_CENTER = 2
        const val GRAVITY_RIGHT = 3
    }

    var offset = java.lang.Float.MIN_VALUE
    lateinit var staticLayout: StaticLayout

    fun init(paint: TextPaint, width: Int, gravity: Int) {
        val align: Layout.Alignment = when (gravity) {
            GRAVITY_LEFT -> Layout.Alignment.ALIGN_NORMAL
            GRAVITY_CENTER -> Layout.Alignment.ALIGN_CENTER
            GRAVITY_RIGHT -> Layout.Alignment.ALIGN_OPPOSITE
            else -> Layout.Alignment.ALIGN_NORMAL
        }
        staticLayout = StaticLayout(text, paint, width, align, 1f, 0f, false)
    }

    fun getHeight(): Int {
        return staticLayout.height
    }

    override fun compareTo(other: LrcEntry): Int {
        return ((time - other.time).toInt())
    }
}

fun parseLrc(lrcFile: File?): List<LrcEntry>? {
    if (lrcFile == null || !lrcFile.exists()) {
        return null
    }

    val entryList = ArrayList<LrcEntry>()
    lrcFile.forEachLine { line ->
        val list = parseLine(line)
        if (list != null && !list.isEmpty()) {
            entryList.addAll(list)
        }
    }
    entryList.sort()
    return entryList
}

fun parseLrc(lrcText: String): List<LrcEntry>? {
    if (TextUtils.isEmpty(lrcText)) {
        return null
    }

    val entryList = ArrayList<LrcEntry>()
    val array = lrcText.split("\\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    for (line in array) {
        val list = parseLine(line)
        if (list != null && !list.isEmpty()) {
            entryList.addAll(list)
        }
    }

    entryList.sort()
    return entryList
}

private fun parseLine(line: String): List<LrcEntry>? {
    var lineTemp = line
    if (TextUtils.isEmpty(lineTemp)) {
        return null
    }

    lineTemp = lineTemp.trim()
    val lineMatcher = Pattern.compile("((\\[\\d\\d:\\d\\d\\.\\d{2,3}\\])+)(.+)").matcher(lineTemp)
    if (!lineMatcher.matches()) {
        return null
    }

    val times = lineMatcher.group(1)
    val text = lineMatcher.group(3)
    val entryList = ArrayList<LrcEntry>()

    val timeMatcher = Pattern.compile("\\[(\\d\\d):(\\d\\d)\\.(\\d){2,3}\\]").matcher(times)
    while (timeMatcher.find()) {
        val min = java.lang.Long.parseLong(timeMatcher.group(1))
        val sec = java.lang.Long.parseLong(timeMatcher.group(2))
        val mil = java.lang.Long.parseLong(timeMatcher.group(3))
        val time = min * DateUtils.MINUTE_IN_MILLIS + sec * DateUtils.SECOND_IN_MILLIS + if (mil >= 100L) mil else mil * 10
        entryList.add(LrcEntry(time, text))
    }
    return entryList
}