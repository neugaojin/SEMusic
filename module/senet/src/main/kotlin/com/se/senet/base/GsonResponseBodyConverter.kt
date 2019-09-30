package com.se.senet.base

import com.google.gson.Gson
import com.google.gson.internal.`$Gson$Types`
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.StringReader

/**
 *Author: gaojin
 *Time: 2018/6/12 上午11:50
 */

class GsonResponseBodyConverter(private val gson: Gson, dataTypeToken: TypeToken<*>, list: List<ConvertIntercepter>) : Converter<ResponseBody, Any> {

    private val mDataTypeToken: TypeToken<*> = dataTypeToken
    private val mConvertIntercepters: List<ConvertIntercepter> = list

    override fun convert(value: ResponseBody): Any {
        var result: Any? = null
        val inputString = value.byteStream().bufferedReader().use { it.readText() }
        val stringReader = StringReader(inputString)
        for (item in mConvertIntercepters) {
            result = item.onConvert(this.mDataTypeToken.type, gson, stringReader)
            if (result != null) {
                break
            }
        }
        if (result == null) {
            stringReader.reset()
            result = gson.fromJson(stringReader, `$Gson$Types`.getRawType(mDataTypeToken.type))
        }
        if (result == null) {
            result = Any()
        }
        return result
    }
}