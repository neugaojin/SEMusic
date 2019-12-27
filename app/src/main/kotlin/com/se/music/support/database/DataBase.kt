package com.se.music.support.database

import com.se.music.entity.AlbumEntity
import com.se.music.entity.ArtistEntity
import com.se.music.entity.MusicEntity
import com.se.music.support.database.metadata.*
import com.se.music.support.singleton.ApplicationSingleton
import com.se.music.support.singleton.SharePreferencesUtils
import com.se.music.support.utils.parseCursorToAlbumEntityList
import com.se.music.support.utils.parseCursorToArtistEntityList
import com.se.music.support.utils.parseCursorToMusicEntityList
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
    suspend fun queryLocalSongCount(): Int {
        return withContext(Dispatchers.IO) {
            val cursor = ApplicationSingleton.instance.contentResolver.query(localMusicUri
                    , infoMusic
                    , songSelection,
                    null
                    , SharePreferencesUtils.getSongSortOrder())
            cursor?.count ?: 0
        }
    }

    /**
     * 获取本地专辑数量
     */
    suspend fun queryLocalAlbumCount(): Int {
        return withContext(Dispatchers.IO) {
            val cursor = ApplicationSingleton.instance.contentResolver.query(localAlbumUri
                    , info_album
                    , albumSelection
                    , null
                    , SharePreferencesUtils.getAlbumSortOrder())
            cursor?.count ?: 0
        }
    }

    /**
     * 获取本地歌手数据
     */
    suspend fun queryLocalArtistCount(): Int {
        return withContext(Dispatchers.IO) {
            val cursor = ApplicationSingleton.instance.contentResolver.query(localSingerUri
                    , info_artist
                    , artistSelection
                    , null
                    , SharePreferencesUtils.getArtistSortOrder())
            cursor?.count ?: 0
        }
    }

    /**
     * 获取本地音乐数据
     */
    suspend fun queryLocalSong(): List<MusicEntity> {
        return withContext(Dispatchers.IO) {
            val musicList = mutableListOf<MusicEntity>()
            val cursor = ApplicationSingleton.instance.contentResolver.query(localMusicUri
                    , infoMusic
                    , songSelection
                    , null
                    , SharePreferencesUtils.getSongSortOrder())
            parseCursorToMusicEntityList(cursor, musicList)
            return@withContext musicList
        }
    }

    /**
     * 获取本地专辑数据
     */
    suspend fun queryLocalAlbum(): List<AlbumEntity> {
        return withContext(Dispatchers.IO) {
            val musicList = mutableListOf<AlbumEntity>()
            val cursor = ApplicationSingleton.instance.contentResolver.query(localAlbumUri
                    , info_album
                    , albumSelection
                    , null
                    , SharePreferencesUtils.getAlbumSortOrder())
            parseCursorToAlbumEntityList(cursor, musicList)
            return@withContext musicList
        }
    }

    /**
     * 获取本地歌手数据
     */
    suspend fun queryLocalArtist(): List<ArtistEntity> {
        return withContext(Dispatchers.IO) {
            val musicList = mutableListOf<ArtistEntity>()
            val cursor = ApplicationSingleton.instance.contentResolver.query(localSingerUri
                    , info_artist
                    , artistSelection
                    , null
                    , SharePreferencesUtils.getArtistSortOrder())
            parseCursorToArtistEntityList(cursor, musicList)
            return@withContext musicList
        }
    }
}