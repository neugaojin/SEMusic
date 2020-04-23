package com.se.music.base.data

import com.se.music.base.data.database.entity.AlbumEntity
import com.se.music.base.data.database.entity.ArtistEntity
import com.se.music.base.data.database.entity.MusicEntity
import com.se.music.base.data.metadata.*
import com.se.music.base.singleton.ApplicationSingleton
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
                    , SharePreferencesUtils.getAlbumSortOrder()).also {
                parseCursorToAlbumEntityList(it, musicList)
            }
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