package com.se.music.support.retrofit

import android.util.Log
import com.se.senet.base.ConverterDataInterceptor
import com.se.senet.base.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

/**
 *Author: gaojin
 *Time: 2018/6/3 下午5:38
 */

object RetrofitFactory {
    enum class Type {
        LAST_FM,
        Ting,
        QQ,
        SO
    }

    fun create(baseUrl: String, type: Type): Retrofit {
        return when (type) {
            Type.LAST_FM -> {
                Retrofit.Builder().baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create()
                                .addConvertIntercepter(ConverterDataInterceptor())) // 设置数据解析器
                        .client(OkHttpClient.Builder()
                                .addInterceptor(LastFmCommonInterceptor())
                                .build())
                        .build()
            }
            Type.Ting -> {
                Retrofit.Builder().baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
                        .client(OkHttpClient.Builder()
                                .addInterceptor(TingCommonInterceptor())
                                .build())
                        .build()
            }
            else -> {
                val logger = HttpLoggingInterceptor { Log.d("SE_API", it) }
                logger.level = HttpLoggingInterceptor.Level.BASIC

                Retrofit.Builder().baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
                        .client(OkHttpClient.Builder()
                                .addInterceptor(logger)
                                .build())
                        .build()
            }
        }
    }
}
