package com.se.senet.base

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 *Author: gaojin
 *Time: 2018/6/11 下午8:39
 */

class GsonConverterFactory private constructor(val mGson: Gson) : Converter.Factory() {

    companion object {
        fun create(): GsonConverterFactory {
            return GsonConverterFactory(GsonFactory.INSTANCE)
        }
    }

    val list: MutableList<ConvertIntercepter> = mutableListOf()

    fun addConvertIntercepter(intercepter: ConvertIntercepter): GsonConverterFactory {
        list.add(intercepter)
        return this
    }

    override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
        return GsonResponseBodyConverter(mGson, TypeToken.get(type), list)
    }

    override fun requestBodyConverter(type: Type, parameterAnnotations: Array<out Annotation>, methodAnnotations: Array<out Annotation>, retrofit: Retrofit): Converter<*, RequestBody>? {
        val adapter = mGson.getAdapter(TypeToken.get(type))
        return GsonRequestBodyConverter(mGson, adapter)
    }
}