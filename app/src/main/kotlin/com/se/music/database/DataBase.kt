package com.se.music.database

import android.content.Context
import com.se.music.database.metadata.infoMusic
import com.se.music.database.metadata.localMusicUri
import com.se.music.database.metadata.songSelection
import com.se.music.singleton.SharePreferencesUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *Author: gaojin
 *Time: 2019-12-04 11:28
 */

object DataBase {
    /**
     * 获取本地音乐的数量
     */
    suspend fun queryLocalSongCount(context: Context): Int {
        return withContext(Dispatchers.IO) {
            val cursor = context.contentResolver.query(localMusicUri, infoMusic, songSelection, null, SharePreferencesUtils.getSongSortOrder())
            cursor?.count ?: 0
        }
    }
}