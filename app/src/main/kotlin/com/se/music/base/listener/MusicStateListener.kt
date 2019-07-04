package com.se.music.base.listener

/**
 *Author: gaojin
 *Time: 2018/5/13 下午5:44
 */

interface MusicStateListener {
    /**
     * 更新歌曲状态信息
     */
    fun updatePlayInfo()

    /**
     * 更新播放时间
     */
    fun updateTime()

    fun reloadAdapter()

    fun musciChanged()

    fun updateLrc()
}