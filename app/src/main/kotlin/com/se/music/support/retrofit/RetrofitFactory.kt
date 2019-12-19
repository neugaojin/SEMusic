package com.se.music.support.retrofit

import com.se.senet.base.ConverterDataInterceptor
import com.se.senet.base.GsonConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

/**
 *Author: gaojin
 *Time: 2018/6/3 下午5:38
 */

object LastFmRetrofitFactory {
    private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(LastFmCommonInterceptor())
            .build()

    fun getInstance(baseUrl: String): Retrofit {
        return Retrofit.Builder().run {
            baseUrl(baseUrl)
            addConverterFactory(GsonConverterFactory.create()
                    .addConvertIntercepter(ConverterDataInterceptor())) // 设置数据解析器
            client(okHttpClient)
            build()
        }
    }
}

object TingRetrofitFactory {
    private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(TingCommonInterceptor())
            .build()

    fun getInstance(baseUrl: String): Retrofit {
        return Retrofit.Builder().run {
            baseUrl(baseUrl)
            addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
            client(okHttpClient)
            build()
        }
    }
}

object QQRetrofitFactory {
    fun getInstance(baseUrl: String): Retrofit {
        return Retrofit.Builder().run {
            baseUrl(baseUrl)
            addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
            build()
        }
    }
}

object RxRetrofitFactory {
    fun getInstance(baseUrl: String): Retrofit {
        return Retrofit.Builder().run {
            baseUrl(baseUrl)
            addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            build()
        }
    }
}
