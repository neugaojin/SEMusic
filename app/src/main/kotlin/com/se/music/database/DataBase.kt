package com.se.music.database

import android.content.Context
import com.se.music.database.metadata.*
import com.se.music.entity.AlbumEntity
import com.se.music.entity.ArtistEntity
import com.se.music.entity.MusicEntity
import com.se.music.singleton.SharePreferencesUtils
import com.se.music.utils.parseCursorToAlbumEntityList
import com.se.music.utils.parseCursorToArtistEntityList
import com.se.music.utils.parseCursorToMusicEntityList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *Author: gaojin
 *Time: 2019-12-04 11:28
 * 读取本地数据库
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

    /**
     * 获取本地音乐数据
     */
    suspend fun queryLocalSong(context: Context): List<MusicEntity> {
        return withContext(Dispatchers.IO) {
            val musicList = mutableListOf<MusicEntity>()
            val cursor = context.contentResolver.query(localMusicUri
                    , infoMusic
                    , songSelection
                    , null
                    , SharePreferencesUtils.getSongSortOrder())
            parseCursorToMusicEntityList(cursor, musicList)
            return@withContext musicList
        }
    }

    /**
     * 获取本地专辑信息
     */
    suspend fun queryLocalAlbum(context: Context): List<AlbumEntity> {
        return withContext(Dispatchers.IO) {
            val musicList = mutableListOf<AlbumEntity>()
            val cursor = context.contentResolver.query(localAlbumUri
                    , info_album
                    , albumSelection
                    , null
                    , SharePreferencesUtils.getAlbumSortOrder())
            parseCursorToAlbumEntityList(cursor, musicList)
            return@withContext musicList
        }
    }

    /**
     * 获取本地歌手信息
     */
    suspend fun queryLocalArtist(context: Context): List<ArtistEntity> {
        return withContext(Dispatchers.IO) {
            val musicList = mutableListOf<ArtistEntity>()
            val cursor = context.contentResolver.query(localSingerUri
                    , info_artist
                    , artistSelection
                    , null
                    , SharePreferencesUtils.getArtistSortOrder())
            parseCursorToArtistEntityList(cursor, musicList)
            return@withContext musicList
        }
    }
}