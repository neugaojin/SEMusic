package com.se.senet.base

import com.google.gson.Gson
import java.io.Reader
import java.lang.reflect.Type

/**
 *Author: gaojin
 *Time: 2018/6/12 上午9:51
 */

interface ConvertIntercepter {
    fun onConvert(type: Type, gson: Gson, reader: Reader): Any?
}