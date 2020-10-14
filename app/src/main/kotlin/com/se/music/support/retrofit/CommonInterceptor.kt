package com.se.music.support.retrofit

import com.se.music.base.singleton.ApplicationSingleton
import okhttp3.Interceptor
import okhttp3.Response
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 *Author: gaojin
 *Time: 2018/6/3 下午5:17
 * 对LastFm的请求添加公用参数
 */

class LastFmCommonInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()

        val oldUrlBuilder = oldRequest.url().newBuilder().apply {
            scheme(oldRequest.url().scheme())
            host(oldRequest.url().host())
            addQueryParameter("format", "json")
            addQueryParameter("api_key", LAST_FM_API_KEY)
        }

        val newRequest = oldRequest.newBuilder().run {
            method(oldRequest.method(), oldRequest.body())
            url(oldUrlBuilder.build())
            build()
        }

        return chain.proceed(newRequest)
    }
}

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class TingCommonInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()
        val oldUrlBuilder = oldRequest.url().newBuilder().apply {
            scheme(oldRequest.url().scheme())
            host(oldRequest.url().host())
            addQueryParameter("format", "json")
        }

        val newRequest = oldRequest.newBuilder().run {
            method(oldRequest.method(), oldRequest.body())
            removeHeader("User-Agent").addHeader("User-Agent", getUserAgent())
            url(oldUrlBuilder.build())
            build()
        }
        return chain.proceed(newRequest)
    }

    private fun getUserAgent(): String {
        val userAgent = System.getProperty("http.agent") ?: ""
        val sb = StringBuffer()
        var i = 0
        val length = userAgent.length
        while (i < length) {
            val c = userAgent[i]
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", c.toInt()))
            } else {
                sb.append(c)
            }
            i++
        }
        return sb.toString()
    }
}

class SoCommonInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()

        val oldUrlBuilder = oldRequest.url().newBuilder().apply {
            scheme(oldRequest.url().scheme())
            host(oldRequest.url().host())
            addQueryParameter("raw", "0")
            addQueryParameter("ownCookie", "0")
        }

        val newRequest = oldRequest.newBuilder().run {
            method(oldRequest.method(), oldRequest.body())
            url(oldUrlBuilder.build())
//            val input = ApplicationSingleton.instance.assets.open("cookie.json")
//            val inputString = BufferedReader(InputStreamReader(input)).useLines { lines ->
//                val results = StringBuilder()
//                lines.forEach { results.append(it) }
//                results.toString()
//            }
//            addHeader("cookie", inputString)
            build()
        }
        return chain.proceed(newRequest)
    }
}