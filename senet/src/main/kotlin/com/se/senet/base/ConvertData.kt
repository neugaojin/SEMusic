package com.se.senet.base

import com.google.gson.JsonElement
import java.io.IOException

/**
 *Author: gaojin
 *Time: 2018/6/12 上午12:13
 */

interface ConvertData<T> {
    @Throws(IOException::class)
    fun convertData(jsonElement: JsonElement): T
}