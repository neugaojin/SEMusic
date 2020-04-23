package com.se.service.data

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import com.se.music.base.data.metadata.*
import com.se.service.extensions.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *Author: gaojin
 *Time: 2020/4/23 11:42 AM
 */

class LocalMusicSource(private val context: Context) : AbstractMusicSource() {

    private val catalog = mutableListOf<MediaMetadataCompat>()

    init {
        state = STATE_INITIALIZING
    }

    override suspend fun load() {
        queryLocalSong().let {
            state = if (catalog.isNotEmpty()) {
                STATE_INITIALIZED
            } else {
                STATE_ERROR
            }
        }
    }

    override fun iterator() = catalog.iterator()

    private suspend fun queryLocalSong() {
        return withContext(Dispatchers.IO) {
            val cursor = context.contentResolver.query(localMusicUri
                    , infoMusic
                    , songSelection,
                    null
                    , MediaStore.Audio.Media.DEFAULT_SORT_ORDER)
            parseCursorToMusicEntityList(cursor, catalog)
        }
    }

    /**
     * 本地音乐Cursor转化为List
     */
    private fun parseCursorToMusicEntityList(cursor: Cursor?, list: MutableList<MediaMetadataCompat>) {
        if (cursor == null) {
            return
        }
        while (cursor.moveToNext()) {
            list.add(createBuilder(cursor).build())
        }
    }

    private fun createBuilder(cursor: Cursor): MediaMetadataCompat.Builder {
        return MediaMetadataCompat.Builder().apply {
            id = cursor.getString(LM_ID_INDEX)
            title = cursor.getString(LM_TITLE_INDEX)
            artist = cursor.getString(LM_ARTIST_INDEX)
            album = cursor.getString(LM_ALBUM_INDEX)
            duration = cursor.getLong(LM_DURATION_INDEX)
            genre = "test"
            mediaUri = "test"
            albumArtUri = "test"
            trackNumber = 1
            trackCount = 0
            flag = MediaBrowserCompat.MediaItem.FLAG_PLAYABLE

            // To make things easier for *displaying* these, set the display properties as well.
            displayTitle = cursor.getString(LM_TITLE_INDEX)
            displaySubtitle = cursor.getString(LM_TITLE_INDEX)
            displayDescription = cursor.getString(LM_TITLE_INDEX) + "displayDesc"
            displayIconUri = cursor.getString(LM_TITLE_INDEX) + "image"
            downloadStatus = MediaDescriptionCompat.STATUS_NOT_DOWNLOADED
        }
    }
}