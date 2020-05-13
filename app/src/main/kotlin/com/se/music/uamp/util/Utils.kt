package com.se.music.uamp.util

import android.support.v4.media.session.PlaybackStateCompat
import com.se.service.library.RepeatMode

/**
 *Author: gaojin
 *Time: 2020/5/12 8:22 PM
 */

fun getRepeatMode(
        @PlaybackStateCompat.RepeatMode repeatMode: Int,
        @PlaybackStateCompat.ShuffleMode shuffleMode: Int
): RepeatMode {
    if (repeatMode == PlaybackStateCompat.REPEAT_MODE_ONE) {
        return RepeatMode.ONE
    } else if (repeatMode == PlaybackStateCompat.REPEAT_MODE_ALL) {
        return if (shuffleMode == PlaybackStateCompat.SHUFFLE_MODE_ALL) {
            RepeatMode.SHUFFLE
        } else {
            RepeatMode.ALL
        }
    }
    return RepeatMode.EMPTY
}