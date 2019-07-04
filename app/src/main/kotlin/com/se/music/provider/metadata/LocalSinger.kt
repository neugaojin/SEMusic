package com.se.music.provider.metadata

import android.provider.MediaStore

/**
 *Author: gaojin
 *Time: 2018/5/28 上午12:42
 */

val localSingerUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI!!

val singerSelection = StringBuilder(MediaStore.Audio.Artists._ID + " in (select distinct " + MediaStore.Audio.Media.ARTIST_ID +
        " from audio_meta where (1=1 )").append(" and " + MediaStore.Audio.Media.SIZE + " > " + mFilterSize)
        .append(" and " + MediaStore.Audio.Media.DURATION + " > " + mFilterDuration)
        .append(")")!!

val info_artist = arrayOf(MediaStore.Audio.Artists.ARTIST, MediaStore.Audio.Artists.NUMBER_OF_TRACKS, MediaStore.Audio.Artists._ID, MediaStore.Audio.Artists.ARTIST_KEY)

const val LS_ID_ARTIST = 0
const val LS_ID_NUMBER_OF_TRACKS = 1
const val LS_ID_INDEX = 2
const val LS_ARTIST_KEY = 3
