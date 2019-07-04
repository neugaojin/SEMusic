package com.se.music.provider.database.entity

/**
 * Author: gaojin
 * Time: 2018/5/6 下午4:28
 */
class MusicInfoCache {
    companion object {
        const val SONG_LIST_ID = "_song_list_id"
        /*Data Field*/
        const val ID = "_id"
        const val SONG_ID = "_song_id"
        const val NAME = "_name"
        const val ALBUM_ID = "_album_id"
        const val ALBUM_NAME = "_album_name"
        const val ALBUM_PIC = "_album_pic"
        const val ARTIST_ID = "_artist_id"
        const val ARTIST_NAME = "_artist_name"
        const val PATH = "_PATH"
        // 0代表本地  1代表不是本地
        const val IS_LOCAL = "_is_local"
        // 0代表不喜欢  1代表喜欢
        const val IS_LOVE = "_is_love"
        const val DURATION = "_duration"
    }
}