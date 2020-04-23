package com.se.music.base.data.database.recent

import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import android.provider.MediaStore
import android.text.TextUtils
import com.se.music.base.data.SharePreferencesUtils
import java.util.*

/**
 * Author: gaojin
 * Time: 2018/5/6 下午5:47
 */
open class SongLoader {

    companion object {
        private val sEmptyList = LongArray(0)

        fun getSongsForCursor(cursor: Cursor?): ArrayList<Song> {
            val arrayList = ArrayList<Song>()
            if (cursor != null && cursor.moveToFirst())
                do {
                    val id = cursor.getLong(0)
                    val title = cursor.getString(1)
                    val artist = cursor.getString(2)
                    val album = cursor.getString(3)
                    val duration = cursor.getInt(4)
                    val trackNumber = cursor.getInt(5)
                    val artistId = cursor.getInt(6).toLong()
                    val albumId = cursor.getLong(7)
                    arrayList.add(Song(id, albumId, artistId, title, artist, album, duration, trackNumber))
                } while (cursor.moveToNext())
            cursor?.close()
            return arrayList
        }

        fun getSongForCursor(cursor: Cursor?): Song {
            var song = Song()
            if (cursor != null && cursor.moveToFirst()) {
                val id = cursor.getLong(0)
                val title = cursor.getString(1)
                val artist = cursor.getString(2)
                val album = cursor.getString(3)
                val duration = cursor.getInt(4)
                val trackNumber = cursor.getInt(5)
                val artistId = cursor.getInt(6).toLong()
                val albumId = cursor.getLong(7)

                song = Song(id, albumId, artistId, title, artist, album, duration, trackNumber)
            }

            cursor?.close()
            return song
        }

        fun getSongListForCursor(cursor: Cursor?): LongArray {
            var cursor: Cursor? = cursor ?: return sEmptyList
            val len = cursor!!.count
            val list = LongArray(len)
            cursor.moveToFirst()
            var columnIndex = -1
            columnIndex = try {
                cursor.getColumnIndexOrThrow(MediaStore.Audio.Playlists.Members.AUDIO_ID)
            } catch (ignored: IllegalArgumentException) {
                cursor.getColumnIndexOrThrow(BaseColumns._ID)
            }

            for (i in 0 until len) {
                list[i] = cursor.getLong(columnIndex)
                cursor.moveToNext()
            }
            cursor.close()
            return list
        }

        fun getAllSongs(context: Context): ArrayList<Song> {
            return getSongsForCursor(makeSongCursor(context, null, null))
        }

        fun getSongForID(context: Context, id: Long): Song {
            return getSongForCursor(makeSongCursor(context, "_id=" + id.toString(), null))
        }

        fun searchSongs(context: Context, searchString: String): ArrayList<Song> {
            return getSongsForCursor(makeSongCursor(context, "title LIKE ?", arrayOf("%$searchString%")))
        }

        fun makeSongCursor(context: Context, selection: String?, paramArrayOfString: Array<String>?): Cursor? {
            var selectionStatement = "is_music=1 AND title != ''"
            val songSortOrder = SharePreferencesUtils.getSongSortOrder()

            if (!TextUtils.isEmpty(selection)) {
                selectionStatement = "$selectionStatement AND $selection"
            }

            return context.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, arrayOf("_id", "title", "artist", "album", "duration", "track", "artistId", "albumId"), selectionStatement, paramArrayOfString, songSortOrder)
        }
    }
}