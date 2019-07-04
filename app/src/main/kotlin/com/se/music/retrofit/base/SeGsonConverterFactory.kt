package com.se.music.retrofit.base

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.se.music.singleton.GsonFactory
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 *Author: gaojin
 *Time: 2018/6/11 下午8:39
 */

class SeGsonConverterFactory private constructor(val mGson: Gson) : Converter.Factory() {

    companion object {
        fun create(): SeGsonConverterFactory {
            return SeGsonConverterFactory(GsonFactory.instance)
        }
    }

    val list: MutableList<ConvertIntercepter> = mutableListOf()

    fun addConvertIntercepter(intercepter: ConvertIntercepter): SeGsonConverterFactory {
        list.add(intercepter)
        return this
    }

    override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
        return SeGsonResponseBodyConverter(mGson, TypeToken.get(type), list)
    }

    override fun requestBodyConverter(type: Type, parameterAnnotations: Array<out Annotation>, methodAnnotations: Array<out Annotation>, retrofit: Retrofit): Converter<*, RequestBody>? {
        val adapter = mGson.getAdapter(TypeToken.get(type))
        return SeGsonRequestBodyConverter(mGson, adapter)
    }
}