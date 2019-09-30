package com.se.senet.callback

import android.content.Context
import androidx.loader.content.Loader
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

/**
 * Created by gaojin on 2017/12/23.
 */
class CallLoader<T>(context: Context, call: Call<T>) : Loader<Try<T>>(context), Callback<T> {
    /**
     * model中创建的Call
     */
    private var rawCall: Call<T> = call
    private var data: Try<T>? = null
    /**
     * retrofit#callback的失败回调
     */
    override fun onFailure(call: Call<T>?, t: Throwable) {
        data = Try.failure(t)
        deliverResult(data)
    }

    /**
     * retrofit#callback的成功回调
     */
    override fun onResponse(call: Call<T>, response: Response<T>) {
        data = if (response.isSuccessful) {
            Try.success(response.body())
        } else {
            Try.failure(HttpException(response))
        }
        //通过Loader分发数据
        if (data != null) {
            deliverResult(data)
        }
    }

    override fun onStartLoading() {
        when {
            rawCall.isCanceled -> deliverCancellation()
            data?.isSuccess == true -> deliverResult(data)
            else -> {
                data = null
                rawCall.enqueue(this)
            }
        }
    }

    override fun onAbandon() {
        if (!rawCall.isCanceled) {
            rawCall.cancel()
        }
        data = null
    }
}