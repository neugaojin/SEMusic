package com.se.music.database.metadata

import android.provider.MediaStore

/**
 *Author: gaojin
 *Time: 2018/5/28 上午1:00
 */

val localAlbumUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI!!

val info_album = arrayOf(MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM, MediaStore.Audio.Albums.NUMBER_OF_SONGS, MediaStore.Audio.Albums.ARTIST, MediaStore.Audio.Albums.ALBUM_KEY)

val albumSelection = StringBuilder(MediaStore.Audio.Albums._ID +
        " in (select distinct " + MediaStore.Audio.Media.ALBUM_ID +
        " from audio_meta where (1=1)")
        .append(" and " + MediaStore.Audio.Media.SIZE + " > " + mFilterSize)
        .append(" and " + MediaStore.Audio.Media.DURATION + " > " + mFilterDuration)
        .append(" )")

const val LA_ID = 0
const val LA_ALBUM = 1
const val LA_SONG_NUMBER = 2
const val LA_ARTIST = 3
const val LA_ALBUM_KEY = 4
