package com.se.music.support.database.metadata

import android.provider.MediaStore

/**
 *Author: gaojin
 *Time: 2018/5/27 下午7:04
 */

val localMusicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI!!
// 查询数据库的列名称
val infoMusic = arrayOf(
        // 音乐ID
        MediaStore.Audio.Media._ID,
        // 音乐的标题
        MediaStore.Audio.Media.TITLE,
        // 日期
        MediaStore.Audio.Media.DATA,
        // 专辑ID
        MediaStore.Audio.Media.ALBUM_KEY,
        // 专辑
        MediaStore.Audio.Media.ALBUM,
        // 艺术家
        MediaStore.Audio.Media.ARTIST,
        // 艺术家ID
        MediaStore.Audio.Media.ARTIST_ID,
        // 音乐时长
        MediaStore.Audio.Media.DURATION,
        // 音乐大小
        MediaStore.Audio.Media.SIZE)

// 用于检索本地文件
const val mFilterSize = 1024 * 1024 // 1MB
const val mFilterDuration = 60 * 1000 // 1分钟
// 查询语句：检索出.mp3为后缀名，时长大于1分钟，文件大小大于1MB的媒体文件
const val songSelection = "1=1 and title != '' and ${MediaStore.Audio.Media.SIZE} > $mFilterSize and ${MediaStore.Audio.Media.DURATION} > $mFilterDuration"

const val LM_ID_INDEX = 0
const val LM_TITLE_INDEX = 1
const val LM_DATA_INDEX = 2
const val LM_ALBUM_KEY_INDEX = 3
const val LM_ALBUM_INDEX = 4
const val LM_ARTIST_INDEX = 5
const val LM_ARTIST_ID_INDEX = 6
const val LM_DURATION_INDEX = 7
const val LM_SIZE_INDEX = 8

// 歌手和专辑列表点击都会进入MyMusic 此时要传递参数表明是从哪里进入的
const val START_FROM_ARTIST = 1
const val START_FROM_ALBUM = 2
const val START_FROM_LOCAL = 3
const val START_FROM_FOLDER = 4
const val START_FROM_FAVORITE = 5
