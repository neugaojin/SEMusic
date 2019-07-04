package com.se.music.singleton

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.se.music.base.Null
import com.se.music.utils.ALBUM_A_Z
import com.se.music.utils.ARTIST_A_Z
import com.se.music.utils.SONG_A_Z

/**
 * Author: gaojin
 * Time: 2018/5/6 下午5:11
 */
class SharePreferencesUtils {

    companion object {
        const val ARTIST_SORT_ORDER = "artist_sort_order"
        const val SONG_SORT_ORDER = "song_sort_order"
        const val ALBUM_SORT_ORDER = "album_sort_order"
        const val DOWNMUSIC_BIT = "DOWNMUSIC_BIT"

        private const val SHARE_PREFERENCE_NAME = "past_music_share_preference"

        val instance: SharePreferencesUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { SharePreferencesUtils() }
    }

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

    fun setPlayLink(id: Long, link: String) {
        mPreferences.edit {
            putString(id.toString() + "", link)
        }
    }

    fun getPlayLink(id: Long): String {
        return mPreferences.getString(id.toString(), null) ?: Null
    }

    fun getDownMusicBit(): Int {
        return mPreferences.getInt(DOWNMUSIC_BIT, 192)
    }
}