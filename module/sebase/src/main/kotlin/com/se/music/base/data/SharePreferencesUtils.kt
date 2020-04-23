package com.se.music.base.data

import android.content.Context
import android.content.SharedPreferences
import com.se.music.base.Null
import com.se.music.base.singleton.ApplicationSingleton

/**
 * Author: gaojin
 * Time: 2018/5/6 下午5:11
 */
object SharePreferencesUtils {

    private const val ARTIST_SORT_ORDER = "artist_sort_order"
    private const val SONG_SORT_ORDER = "song_sort_order"
    private const val ALBUM_SORT_ORDER = "album_sort_order"
    private const val DOWN_MUSIC_BIT = "DOWN_MUSIC_BIT"

    private const val SHARE_PREFERENCE_NAME = "se_music_share_preference"

    private val mPreferences: SharedPreferences = ApplicationSingleton.instance.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getArtistSortOrder(): String {
        return mPreferences.getString(ARTIST_SORT_ORDER, ARTIST_A_Z) ?: Null
    }

    fun getSongSortOrder(): String {
        return mPreferences.getString(SONG_SORT_ORDER, SONG_A_Z) ?: Null
    }

    fun getAlbumSortOrder(): String {
        return mPreferences.getString(ALBUM_SORT_ORDER, ALBUM_A_Z) ?: Null
    }

    fun getPlayLink(id: Long): String {
        return mPreferences.getString(id.toString(), null) ?: Null
    }

    fun getDownMusicBit(): Int {
        return mPreferences.getInt(DOWN_MUSIC_BIT, 192)
    }
}