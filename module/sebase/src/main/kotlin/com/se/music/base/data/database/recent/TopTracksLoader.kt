package com.se.music.base.data.database.recent

import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import com.se.music.base.data.database.provider.RecentStore

/**
 * Author: gaojin
 * Time: 2018/5/6 下午6:24
 */
class TopTracksLoader(type: QueryType) : SongLoader() {

    init {
        mQueryType = type
    }

    companion object {
        val NUMBER_OF_SONGS = 99
        lateinit var mQueryType: QueryType

        fun getCursor(context: Context, queryType: QueryType): Cursor? {
            mQueryType = queryType
            return getCursor(context)
        }

        fun getCount(context: Context, queryType: QueryType): Int {
            val cursor = getCursor(context, queryType)
            return cursor?.count ?: 0
        }

        fun getCursor(context: Context): Cursor? {
            var retCursor: SortedCursor? = null
            if (mQueryType === QueryType.TopTracks) {
                retCursor = makeTopTracksCursor(context)
            } else if (mQueryType === QueryType.RecentSongs) {
                retCursor = makeRecentTracksCursor(context)
            }

            if (retCursor != null) {
                val missingIds = retCursor.getMissingIds()
                if (missingIds.size > 0) {
                    for (id in missingIds) {
                        if (mQueryType === QueryType.TopTracks) {
                            SongPlayCount.instance.removeItem(id)
                        } else if (mQueryType === QueryType.RecentSongs) {
                            RecentStore.instance.removeItem(id)
                        }
                    }
                }
            }

            return retCursor
        }

        fun makeTopTracksCursor(context: Context): SortedCursor? {

            val songs: Cursor? = SongPlayCount.instance.getTopPlayedResults(NUMBER_OF_SONGS)

            try {
                return makeSortedCursor(context, songs,
                        songs!!.getColumnIndex(SongPlayCount.SongPlayCountColumns.ID))
            } finally {
                if (songs != null) {
                    songs.close()
                }
            }
        }

        fun makeRecentTracksCursor(context: Context): SortedCursor? {

            var songs = RecentStore.instance.queryRecentIds(null)

            try {
                return makeSortedCursor(context, songs,
                        songs!!.getColumnIndex(SongPlayCount.SongPlayCountColumns.ID))
            } finally {
                if (songs != null) {
                    songs.close()
                    songs = null
                }
            }
        }

        private fun makeSortedCursor(
            context: Context,
            cursor: Cursor?,
            idColumn: Int
        ): SortedCursor? {
            if (cursor != null && cursor.moveToFirst()) {
                val selection = StringBuilder()
                selection.append(BaseColumns._ID)
                selection.append(" IN (")

                val order = LongArray(cursor.count)

                var id = cursor.getLong(idColumn)
                selection.append(id)
                order[cursor.position] = id

                while (cursor.moveToNext()) {
                    selection.append(",")

                    id = cursor.getLong(idColumn)
                    order[cursor.position] = id
                    selection.append(id.toString())
                }

                selection.append(")")

                val songCursor = makeSongCursor(context, selection.toString(), null)
                if (songCursor != null) {
                    return SortedCursor(songCursor, order, BaseColumns._ID, null!!)
                }
            }

            return null
        }
    }

    enum class QueryType {
        TopTracks,
        RecentSongs
    }
}