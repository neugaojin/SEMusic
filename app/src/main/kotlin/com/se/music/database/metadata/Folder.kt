package com.se.music.database.metadata

import android.provider.MediaStore

/**
 *Author: gaojin
 *Time: 2018/5/28 上午1:15
 */

val folderUri = MediaStore.Files.getContentUri("external")!!

val info_folder = arrayOf(MediaStore.Files.FileColumns.DATA)

// 筛选条件
val folderSelection = StringBuilder(MediaStore.Files.FileColumns.MEDIA_TYPE +
        " = " + MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO + " and " + "(" +
        MediaStore.Files.FileColumns.DATA + " like'%.mp3' or " + MediaStore.Audio.Media.DATA +
        " like'%.wma')")
        // 查询语句：检索出.mp3为后缀名，时长大于1分钟，文件大小大于1MB的媒体文件
        .append(" and " + MediaStore.Audio.Media.SIZE + " > " + mFilterSize)
        .append(" and " + MediaStore.Audio.Media.DURATION + " > " + mFilterDuration)
        .append(") group by ( " + MediaStore.Files.FileColumns.PARENT)!!