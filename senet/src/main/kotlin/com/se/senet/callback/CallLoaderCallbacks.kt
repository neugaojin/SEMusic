package com.se.senet.callback

import android.content.Context
import android.os.Bundle
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import retrofit2.Call

/**
 * Created by gaojin on 2017/12/23.
 */
abstract class CallLoaderCallbacks<E> constructor(private val context: Context) : LoaderManager.LoaderCallbacks<Try<E>> {

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Try<E>> {
        return CallLoader(context, onCreateCall(id, args))
    }

    override fun onLoadFinished(loader: Loader<Try<E>>, data: Try<E>?) {
        if (data?.isSuccess()!!) {
            onSuccess(loader, data.get()!!)
        } else {
            onFailure(loader, data.throwable()!!)
        }
    }

    override fun onLoaderReset(loader: Loader<Try<E>>) {
    }

    abstract fun onCreateCall(id: Int, args: Bundle?): Call<E>

    abstract fun onSuccess(loader: Loader<*>, data: E)

    abstract fun onFailure(loader: Loader<*>, throwable: Throwable)
}