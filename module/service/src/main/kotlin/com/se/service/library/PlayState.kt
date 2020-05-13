package com.se.service.library

/**
 *Author: gaojin
 *Time: 2020/4/28 7:26 PM
 */
/**
 * 播放状态
 */
enum class PlayState {
    EMPTY, PLAYING, PAUSE, STOP
}

enum class RepeatMode(val value: Int) {
    EMPTY(-1), ALL(0), ONE(1), SHUFFLE(2)
}