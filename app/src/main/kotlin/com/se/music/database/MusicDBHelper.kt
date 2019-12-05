package com.se.music.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.se.music.base.DATABASE_NAME
import com.se.music.base.DATABASE_VERSION
import com.se.music.database.database.entity.MusicInfoCache
import com.se.music.database.database.provider.ImageStore
import com.se.music.database.database.provider.LoveSongStore
import com.se.music.database.database.provider.RecentStore
import com.se.music.database.metadata.*
import com.se.music.singleton.ApplicationSingleton

/**
 * Author: gaojin
 * Time: 2018/5/6 下午1:52
 */
class MusicDBHelper(context: Context, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    companion object {
        const val SONG_LIST_TABLE = "SongListTable"
        const val MUSIC_INFO_TABLE = "MusicInfoTable"
        const val LOVE_SONG_TABLE = "LoveSongTable"
        const val IMAGE_TABLE = "ImageTable"
        val instance: MusicDBHelper by lazy { MusicDBHelper() }

        const val SONG_LIST_TABLE_CREATE = "create table $SONG_LIST_TABLE " +
                "($SL_ID varchar(128), " +
                "$SL_NAME varchar(50) NOT NULL," +
                "$SL_COUNT int," +
                "$SL_CREATOR varchar(20)," +
                "$SL_CREATE_TIME varchar(30) NOT NULL," +
                "$SL_PIC varchar(50)," +
                "$SL_INFO varchar(50)," +
                "PRIMARY KEY ($SL_ID)" +
                ");"
        const val MUSIC_INFO_TABLE_CREATE = "create table " + MUSIC_INFO_TABLE +
                " (" + MusicInfoCache.SONG_ID + " varchar(128), " +
                MusicInfoCache.SONG_LIST_ID + " varchar(50) NOT NULL," +
                MusicInfoCache.NAME + " varchar(50) NOT NULL," +
                MusicInfoCache.ALBUM_ID + " varchar(20)," +
                MusicInfoCache.ALBUM_NAME + " varchar(20)," +
                MusicInfoCache.ALBUM_PIC + " varchar(50)," +
                MusicInfoCache.ARTIST_ID + " varchar(20) NOT NULL," +
                MusicInfoCache.ARTIST_NAME + " varchar(10) NOT NULL," +
                MusicInfoCache.PATH + " varchar(20)," +
                MusicInfoCache.IS_LOCAL + " int," +
                MusicInfoCache.ID + " varchar(128)," +
                MusicInfoCache.IS_LOVE + " int," +
                "PRIMARY KEY (" + MusicInfoCache.ID + ")" +
                ");"
    }

    constructor() : this(ApplicationSingleton.instance.applicationContext, DATABASE_NAME, null, DATABASE_VERSION)

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SONG_LIST_TABLE_CREATE)
        db.execSQL(MUSIC_INFO_TABLE_CREATE)
        RecentStore.instance.onCreate(db)
        ImageStore.instance.onCreate(db)
        LoveSongStore.instance.onCreate(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $SONG_LIST_TABLE")
        db.execSQL("DROP TABLE IF EXISTS $MUSIC_INFO_TABLE")
        RecentStore.instance.onUpgrade(db, oldVersion, newVersion)
        ImageStore.instance.onUpgrade(db, oldVersion, newVersion)
        LoveSongStore.instance.onUpgrade(db, oldVersion, newVersion)
        onCreate(db)
    }
}