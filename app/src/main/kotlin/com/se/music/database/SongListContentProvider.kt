package com.se.music.database

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import com.se.music.database.metadata.SL_AUTHORITIES
import com.se.music.database.metadata.SONG_LIST_CONTENT_URI

/**
 *Author: gaojin
 *Time: 2018/5/19 下午10:44
 *自建数据库查询Provider
 */

private const val SONG_LIST_CODE = 1
private const val LOVE_SONG_CODE = 2
private const val MUSIC_INFO_CODE = 3

class SongListContentProvider : ContentProvider() {
    private var uriMatcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    private lateinit var dbHelper: MusicDBHelper

    init {
        uriMatcher.addURI(SL_AUTHORITIES, "/${MusicDBHelper.SONG_LIST_TABLE}", SONG_LIST_CODE)
        uriMatcher.addURI(SL_AUTHORITIES, "/${MusicDBHelper.LOVE_SONG_TABLE}", LOVE_SONG_CODE)
        uriMatcher.addURI(SL_AUTHORITIES, "/${MusicDBHelper.MUSIC_INFO_TABLE}", MUSIC_INFO_CODE)
    }

    override fun onCreate(): Boolean {
        dbHelper = MusicDBHelper.instance
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        val db = dbHelper.writableDatabase
        val rowId = when (uriMatcher.match(uri)) {
            SONG_LIST_CODE -> {
                db.insert(MusicDBHelper.SONG_LIST_TABLE, null, values)
            }
            LOVE_SONG_CODE -> {
                db.insert(MusicDBHelper.LOVE_SONG_TABLE, null, values)
            }
            MUSIC_INFO_CODE -> {
                db.insert(MusicDBHelper.MUSIC_INFO_TABLE, null, values)
            }
            else -> {
                db.insert(MusicDBHelper.SONG_LIST_TABLE, null, values)
            }
        }

        if (rowId > 0) {
            val rowUri = ContentUris.withAppendedId(SONG_LIST_CONTENT_URI, rowId)
            context?.contentResolver?.notifyChange(rowUri, null)
            return rowUri
        }
        throw SQLException("Failed to insert row into" + uri + "嘤嘤嘤")
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
        val queryBuilder = SQLiteQueryBuilder()
        val db = dbHelper.readableDatabase
        val c: Cursor
        when (uriMatcher.match(uri)) {
            SONG_LIST_CODE -> {
                queryBuilder.tables = MusicDBHelper.SONG_LIST_TABLE
                c = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder)
            }
            LOVE_SONG_CODE -> {
                queryBuilder.tables = MusicDBHelper.LOVE_SONG_TABLE
                c = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder)
            }
            MUSIC_INFO_CODE -> {
                queryBuilder.tables = MusicDBHelper.MUSIC_INFO_TABLE
                c = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder)
            }
            else -> {
                queryBuilder.tables = ""
                c = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder)
            }
        }
        c.setNotificationUri(context?.contentResolver, uri)
        return c
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = dbHelper.writableDatabase
        val count: Int
        count = when (uriMatcher.match(uri)) {
            SONG_LIST_CODE -> {
                db.update(MusicDBHelper.SONG_LIST_TABLE, values, selection, selectionArgs)
            }
            LOVE_SONG_CODE -> {
                db.update(MusicDBHelper.LOVE_SONG_TABLE, values, selection, selectionArgs)
            }
            MUSIC_INFO_CODE -> {
                db.update(MusicDBHelper.MUSIC_INFO_TABLE, values, selection, selectionArgs)
            }
            else -> {
                db.update(MusicDBHelper.SONG_LIST_TABLE, values, selection, selectionArgs)
            }
        }
        context?.contentResolver?.notifyChange(uri, null)
        return count
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = dbHelper.writableDatabase
        val count: Int
        count = when (uriMatcher.match(uri)) {
            SONG_LIST_CODE -> {
                db.delete(MusicDBHelper.SONG_LIST_TABLE, selection, selectionArgs)
            }
            LOVE_SONG_CODE -> {
                db.delete(MusicDBHelper.LOVE_SONG_TABLE, selection, selectionArgs)
            }
            MUSIC_INFO_CODE -> {
                db.delete(MusicDBHelper.MUSIC_INFO_TABLE, selection, selectionArgs)
            }
            else -> {
                db.delete(MusicDBHelper.SONG_LIST_TABLE, selection, selectionArgs)
            }
        }
        context?.contentResolver?.notifyChange(uri, null)
        return count
    }

    override fun getType(uri: Uri): String {
        return ""
    }
}