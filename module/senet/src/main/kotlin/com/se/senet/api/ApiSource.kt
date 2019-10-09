package com.se.senet.api

import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 *Author: gaojin
 *Time: 2019-09-30 16:20
 */

suspend fun <T> Call<T>.await(): T {
    return suspendCancellableCoroutine { it ->
        it.invokeOnCancellation {
            it?.printStackTrace()
            cancel()
        }
        enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                it.resumeWithException(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    it.resume(response.body()!!)
                } else {
                    it.resumeWithException(Throwable(response.toString()))
                }
            }
        })
    }
}