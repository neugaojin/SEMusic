package com.se.music.utils.manager

/**
 *Author: gaojin
 *Time: 2018/10/24 下午11:43
 */

interface PlayTimeListener {
    fun playedTime(position: Long, duration: Long)
}