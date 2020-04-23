package com.se.music.base.data.database.provider

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.se.music.base.data.MusicDBHelper

/**
 *Author: gaojin
 *Time: 2018/7/8 下午5:34
 * 图片缓存管理
 */
class ImageStore {
    companion object {
        private const val MAX_ITEMS_IN_DB = 100

        const val IMAGE_KEY = "key"
        const val IMAGE_VALUE = "image_value"
        private const val IMAGE_TABLE_CREATE = "create table " + MusicDBHelper.IMAGE_TABLE +
                " (" + IMAGE_KEY + " int, " +
                IMAGE_VALUE + " varchar(50)," +
                "PRIMARY KEY (" + IMAGE_KEY + ")" +
                ");"
        val instance: ImageStore by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { ImageStore() }
    }

    fun onCreate(db: SQLiteDatabase) {
        db.execSQL(IMAGE_TABLE_CREATE)
    }

    fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${MusicDBHelper.IMAGE_TABLE}")
    }

    /**
     * 添加图片缓存
     */
    @Synchronized
    fun addImage(key: Int, imageValue: String) {
        if (imageValue.isEmpty()) {
            return
        }
        val database = MusicDBHelper.instance.writableDatabase
        database.beginTransaction()
        val value = ContentValues(2)
        value.put(IMAGE_KEY, key)
        value.put(IMAGE_VALUE, imageValue)
        database.insert(MusicDBHelper.IMAGE_TABLE, null, value)
        val sql = "SELECT * FROM ${MusicDBHelper.IMAGE_TABLE}"
        database.rawQuery(sql, null).use {
            if (it.count > MAX_ITEMS_IN_DB) {
                // 删除第一个
                it.moveToPosition(0)
                val cachedKey = it.getString(0)
                database.delete(MusicDBHelper.IMAGE_TABLE, "$IMAGE_KEY=$cachedKey", null)
            }
        }
        database.setTransactionSuccessful()
        database.endTransaction()
    }

    /**
     * 查询图片缓存
     */
    @Synchronized
    fun query(key: Int): String? {
        val database = MusicDBHelper.instance.writableDatabase
        database.beginTransaction()
        var imageValue: String? = null
        val sql = "SELECT $IMAGE_VALUE FROM ${MusicDBHelper.IMAGE_TABLE} WHERE key='$key'"
        database.rawQuery(sql, null).use {
            if (it.moveToFirst()) {
                imageValue = it.getString(0)
            }
        }
        database.setTransactionSuccessful()
        database.endTransaction()
        return imageValue
    }
}