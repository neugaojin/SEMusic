package com.se.senet.callback

import android.content.Context
import android.os.AsyncTask
import androidx.loader.content.Loader
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

/**
 * Created by gaojin on 2017/12/23.
 */
class CallLoader<T>(context: Context, private val onStartErrorResume: Boolean) : Loader<Try<T>>(context), Callback<T> {

    private var rawCall: Call<T>? = null
    private var data: Try<T>? = null
    private var executing: Call<T>? = null
    private var callCreator: CallCreator<T>? = null

    constructor(context: Context, call: Call<T>, onStartErrorResume: Boolean) : this(context, onStartErrorResume) {
        this.rawCall = call
    }

    override fun onFailure(call: Call<T>?, t: Throwable) {
        data = Try.failure(t)
        deliverResult(data)
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        data = if (response.isSuccessful) {
            Try.success(response.body()!!)
        } else {
            Try.failure(HttpException(response))
        }
        deliverResult(data)
    }

    override fun onStartLoading() {
        if (rawCall != null && rawCall!!.isCanceled) {
            deliverCancellation()
        } else if (data != null && (data!!.isSuccess() || !onStartErrorResume)) {
            deliverResult(data)
        } else {
            data = null
            if (rawCall != null) {
                executing = rawCall!!.clone()
                executing!!.enqueue(this@CallLoader)
            } else if (callCreator != null) {
                AsyncTask.THREAD_POOL_EXECUTOR.execute(Runnable {
                    if (isAbandoned) {
                        return@Runnable
                    }
                    rawCall = callCreator!!.createCall()
                    if (rawCall != null) {
                        executing = rawCall!!.clone()
                        executing!!.enqueue(this@CallLoader)
                    }
                })
            }
        }
    }

    override fun onAbandon() {
        if (executing != null) {
            if (!executing!!.isCanceled) {
                executing!!.cancel()
            }
            executing = null
        }
        data = null
    }

    interface CallCreator<D> {
        fun createCall(): Call<D>
    }
}