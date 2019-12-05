package com.se.music.database.database.provider

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.se.music.database.MusicDBHelper
import com.se.music.database.database.entity.MusicInfoCache

/**
 *Author: gaojin
 *Time: 2019/1/9 7:57 PM
 */

class LoveSongStore {

    companion object {

        const val LOVE_SONG_TABLE_CREATE = "create table " + MusicDBHelper.LOVE_SONG_TABLE +
                " (" + MusicInfoCache.SONG_ID + " integer(128), " +
                MusicInfoCache.NAME + " varchar(50) NOT NULL," +
                MusicInfoCache.ALBUM_ID + " integer(20)," +
                MusicInfoCache.ALBUM_NAME + " varchar(20)," +
                MusicInfoCache.ALBUM_PIC + " varchar(50)," +
                MusicInfoCache.ARTIST_ID + " integer(20)," +
                MusicInfoCache.ARTIST_NAME + " varchar(10) NOT NULL," +
                MusicInfoCache.PATH + " varchar(20)," +
                MusicInfoCache.IS_LOCAL + " int," +
                MusicInfoCache.DURATION + " integer," +
                "PRIMARY KEY (" + MusicInfoCache.SONG_ID + ")" +
                ");"

        val instance: LoveSongStore by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { LoveSongStore() }
    }

    fun onCreate(db: SQLiteDatabase) {
        db.execSQL(LOVE_SONG_TABLE_CREATE)
    }

    fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${MusicDBHelper.LOVE_SONG_TABLE}")
    }

    fun addSong(value: ContentValues) {
    }

    fun hasSong(key: Long): Boolean {
        val database = MusicDBHelper.instance.writableDatabase
        database.beginTransaction()
        var hasValue = false
        val sql = "SELECT ${MusicInfoCache.SONG_ID} FROM ${MusicDBHelper.LOVE_SONG_TABLE} WHERE key='$key'"
        database.rawQuery(sql, null).use {
            if (it.count > 0) {
                hasValue = true
            }
        }
        database.setTransactionSuccessful()
        database.endTransaction()
        return hasValue
    }
}