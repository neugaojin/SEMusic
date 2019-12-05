package com.se.music.database.metadata

import android.net.Uri
import com.se.music.database.MusicDBHelper

/**
 *Author: gaojin
 *Time: 2018/5/27 下午6:55
 */

const val SL_AUTHORITIES = "com.se.music.SEContentProvider"
val SONG_LIST_CONTENT_URI = Uri.parse("content://$SL_AUTHORITIES/${MusicDBHelper.SONG_LIST_TABLE}")!!
val LOVE_SONG_CONTENT_URI = Uri.parse("content://$SL_AUTHORITIES/${MusicDBHelper.LOVE_SONG_TABLE}")!!

/*Data Field*/
const val SL_ID = "_id"
const val SL_NAME = "_name"
const val SL_COUNT = "_count"
const val SL_CREATOR = "_creator"
const val SL_CREATE_TIME = "_create_time"
const val SL_PIC = "_pic"
const val SL_INFO = "_info"

/*Index*/
const val SL_ID_INDEX = 0
const val SL_NAME_INDEX = 1
const val SL_COUNT_INDEX = 2
const val SL_CREATOR_INDEX = 3
const val SL_CREATE_TIME_INDEX = 4
const val SL_PIC_INDEX = 5
const val SL_INFO_INDEX = 6