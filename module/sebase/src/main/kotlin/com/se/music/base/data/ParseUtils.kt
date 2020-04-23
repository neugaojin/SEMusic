package com.se.music.base.data

import android.database.Cursor
import com.se.music.base.Null
import com.se.music.base.data.database.entity.AlbumEntity
import com.se.music.base.data.database.entity.ArtistEntity
import com.se.music.base.data.database.entity.MusicEntity
import com.se.music.base.data.database.entity.SongListEntity
import com.se.music.base.data.database.provider.ImageStore
import com.se.music.base.data.metadata.*

/**
 *Author: gaojin
 *Time: 2018/5/27 下午2:08
 */

/**
 * 歌单Cursor转化List
 */
fun parseCursorToSongList(id: Int, cursor: Cursor): MutableList<SongListEntity> {
    val list = ArrayList<SongListEntity>()
    while (cursor.moveToNext()) {
        val songListEntity = SongListEntity(cursor.getString(SL_ID_INDEX), cursor.getString(SL_NAME_INDEX), cursor.getString(SL_CREATE_TIME_INDEX))

        songListEntity.count = cursor.getInt(SL_COUNT_INDEX)
        songListEntity.creator = cursor.getString(SL_CREATOR_INDEX)
        songListEntity.listPic = cursor.getString(SL_PIC_INDEX)
        songListEntity.info = cursor.getString(SL_INFO_INDEX)
        list.add(songListEntity)
    }
    return list
}

/**
 * 本地音乐Cursor转化为List
 */
fun parseCursorToMusicEntityList(cursor: Cursor?, list: MutableList<MusicEntity>) {
    if (cursor == null) {
        return
    }
    while (cursor.moveToNext()) {
        val musicEntity = MusicEntity(cursor.getLong(LM_ID_INDEX)
                , cursor.getString(LM_TITLE_INDEX)
                , cursor.getString(LM_ARTIST_INDEX)
                , null
                , cursor.getString(LM_ALBUM_KEY_INDEX)
                , cursor.getLong(LM_DURATION_INDEX)
                , cursor.getString(LM_ALBUM_INDEX)
                , cursor.getLong(LM_ARTIST_ID_INDEX)
                , null, cursor.getLong(LM_SIZE_INDEX)
                , null
                , null,
                null
                , 0
                , false)
        list.add(musicEntity)
    }
}

/**
 * 歌手Cursor转换List
 */
fun parseCursorToArtistEntityList(cursor: Cursor?, list: MutableList<ArtistEntity>) {
    if (cursor == null) {
        return
    }
    while (cursor.moveToNext()) {
        val artistEntity = ArtistEntity(cursor.getString(LS_ID_ARTIST)
                , cursor.getInt(LS_ID_NUMBER_OF_TRACKS)
                , cursor.getInt(LS_ID_INDEX)
                , cursor.getString(LS_ARTIST_KEY))
        artistEntity.imageId = ImageStore.instance.query(cursor.getString(LS_ID_ARTIST).hashCode())
                ?: Null
        list.add(artistEntity)
    }
}

/**
 * 专辑Cursor转换List
 */
fun parseCursorToAlbumEntityList(cursor: Cursor?, list: MutableList<AlbumEntity>) {
    if (cursor == null) {
        return
    }
    while (cursor.moveToNext()) {
        val albumEntity = AlbumEntity(cursor.getInt(LA_ID).toLong()
                , cursor.getString(LA_ALBUM)
                , cursor.getInt(LA_SONG_NUMBER)
                , cursor.getString(LA_ARTIST)
                , cursor.getString(LA_ALBUM_KEY))
        albumEntity.imageUrl = ImageStore.instance.query(cursor.getString(LA_ALBUM).hashCode())
                ?: Null
        list.add(albumEntity)
    }
}

/**
 * 本地音乐Cursor转化为List
 */
fun parseLoveSongCursorToMusicEntityList(id: Int, cursor: Cursor, list: MutableList<MusicEntity>) {
    while (cursor.moveToNext()) {
        val musicEntity = MusicEntity(cursor.getLong(LOVE_ID_INDEX), cursor.getString(LOVE_NAME_INDEX), cursor.getString(LOVE_ARTIST_NAME_INDEX), null, cursor.getString(LOVE_ALBUN_ID_INDEX), cursor.getLong(LOVE_DURATION), cursor.getString(LOVE_ALBUM_NAME_INDEX), cursor.getLong(LOVE_ARTIST_ID_INDEX), null, 0, null, null,
                null, 0, cursor.getInt(LOVE_IS_LOCAL) == 0)
        list.add(musicEntity)
    }
}