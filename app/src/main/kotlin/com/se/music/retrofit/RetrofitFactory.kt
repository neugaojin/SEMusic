package com.se.music.retrofit

import com.se.music.retrofit.base.ConverterDataInterceptor
import com.se.music.retrofit.base.SeGsonConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *Author: gaojin
 *Time: 2018/6/3 下午5:38
 */

class LastFmRetrofitFactory {
    companion object {
        private val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(LastFmCommonInterceptor())
                .build()!!

        fun getInstance(baseUrl: String): Retrofit {
            return Retrofit.Builder().run {
                baseUrl(baseUrl)
                addConverterFactory(SeGsonConverterFactory.create()
                        .addConvertIntercepter(ConverterDataInterceptor())) // 设置数据解析器
                client(okHttpClient)
                build()
            }
        }
    }
}

class TingRetrofitFactory {
    companion object {
        private val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(TingCommonInterceptor())
                .build()!!

        fun getInstance(baseUrl: String): Retrofit {
            return Retrofit.Builder().run {
                baseUrl(baseUrl)
                addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
                client(okHttpClient)
                build()
            }
        }
    }
}

class QQRetrofitFactory {
    companion object {
        fun getInstance(baseUrl: String): Retrofit {
            return Retrofit.Builder().run {
                baseUrl(baseUrl)
                addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
                build()!!
            }
        }
    }
}
