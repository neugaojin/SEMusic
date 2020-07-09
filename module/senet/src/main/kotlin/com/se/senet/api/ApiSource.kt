package com.se.senet.api

import com.se.music.base.log.Loger
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
    return suspendCancellableCoroutine { cancellableContinuation ->
        cancellableContinuation.invokeOnCancellation {
            it?.printStackTrace()
            cancel()
        }
        Loger.e("ApiSource") {
            request().url().host() +
                    request().url().encodedPath()
        }
        enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                cancellableContinuation.resumeWithException(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                if (body != null && response.isSuccessful) {
                    cancellableContinuation.resume(body)
                } else {
                    cancellableContinuation.resumeWithException(Throwable(response.toString()))
                }
            }
        })
    }
}