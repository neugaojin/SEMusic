package com.se.music.singleton

import com.se.music.base.Null
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

/**
 *Author: gaojin
 *Time: 2018/10/23 下午11:15
 */

object OkHttpSingleton {
    private val mOkHttpClient = OkHttpClient.Builder()
            .readTimeout(1000, TimeUnit.MINUTES)
            .writeTimeout(1000, TimeUnit.MINUTES)
            .connectTimeout(1000, TimeUnit.MINUTES)
            .build()!!

    fun getResponseString(action: String): String {
        try {
            val request = Request.Builder()
                    .url(action)
                    .build()
            val response = mOkHttpClient.newCall(request).execute()
            if (response.isSuccessful) {
                val c = response.body()?.string()
                return c ?: Null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Null
    }
}