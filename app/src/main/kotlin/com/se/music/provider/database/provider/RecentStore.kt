package com.se.music.provider.database.provider

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.se.music.provider.MusicDBHelper
import java.util.*

/**
 * Author: gaojin
 * Time: 2018/5/6 下午2:42
 */
class RecentStore {

    companion object {
        val instance: RecentStore by lazy { RecentStore() }
        private const val MAX_ITEMS_IN_DB = 100
    }

    fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + RecentStoreColumns.NAME + " (" +
                RecentStoreColumns.ID + " LONG NOT NULL," + RecentStoreColumns.TIMEPLAYED +
                " LONG NOT NULL);")
    }

    fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + RecentStoreColumns.NAME)
        onCreate(db)
    }

    @Synchronized
    fun addSongId(songId: Long) {
        val database = MusicDBHelper.instance.writableDatabase
        database.beginTransaction()
        try {
            var mostRecentItem: Cursor? = null
            try {
                mostRecentItem = queryRecentIds("1")
                if (mostRecentItem != null && mostRecentItem.moveToFirst()) {
                    if (songId == mostRecentItem.getLong(0)) {
                        return
                    }
                }
            } finally {
                mostRecentItem?.close()
            }

            val values = ContentValues(2)
            values.put(RecentStoreColumns.ID, songId)
            values.put(RecentStoreColumns.TIMEPLAYED, System.currentTimeMillis())
            database.insert(RecentStoreColumns.NAME, null, values)

            var oldest: Cursor? = null
            try {
                oldest = database.query(RecentStoreColumns.NAME,
                        arrayOf(RecentStoreColumns.TIMEPLAYED), null, null, null, null,
                        RecentStoreColumns.TIMEPLAYED + " ASC")

                if (oldest != null && oldest.count > MAX_ITEMS_IN_DB) {
                    oldest.moveToPosition(oldest.count - MAX_ITEMS_IN_DB)
                    val timeOfRecordToKeep = oldest.getLong(0)

                    database.delete(RecentStoreColumns.NAME,
                            RecentStoreColumns.TIMEPLAYED + " < ?",
                            arrayOf(timeOfRecordToKeep.toString()))
                }
            } finally {
                oldest?.close()
            }
        } finally {
            database.setTransactionSuccessful()
            database.endTransaction()
        }
    }

    @Synchronized
    fun removeItem(songId: Long) {
        val database = MusicDBHelper.instance.writableDatabase
        database.delete(RecentStoreColumns.NAME, RecentStoreColumns.ID + " = ?", arrayOf(songId.toString()))
    }

    fun deleteAll() {
        val database = MusicDBHelper.instance.writableDatabase
        database.delete(RecentStoreColumns.NAME, null, null)
    }

    @Synchronized
    fun queryRecentIds(limit: String?): Cursor? {
        val database = MusicDBHelper.instance.readableDatabase
        return database.query(RecentStoreColumns.NAME,
                arrayOf(RecentStoreColumns.ID), null, null, null, null,
                RecentStoreColumns.TIMEPLAYED + " DESC", limit)
    }

    @Synchronized
    fun getRecentIds(): LongArray {
        val cursor = queryRecentIds(null) ?: return LongArray(0)
        val list = ArrayList<Long>()
        while (cursor.moveToNext()) {
            list.add(cursor.getLong(cursor.getColumnIndex(RecentStoreColumns.ID)))
        }
        cursor.close()
        val l = LongArray(list.size)
        for (i in list.indices) {
            l[i] = list[i]
        }
        return l
    }

    interface RecentStoreColumns {
        companion object {
            /* Table name */
            val NAME = "recenthistory"

            /* Album IDs column */
            val ID = "songid"

            /* Time played column */
            val TIMEPLAYED = "timeplayed"
        }
    }
}