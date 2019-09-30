package com.se.senet.callback

/**
 * Created by gaojin on 2017/12/23.
 * out:只能在输出位置
 * in:只能在输入位置
 */
abstract class Try<out T> {

    companion object {
        fun <T> success(t: T?): Try<T>? {
            return Success(t)
        }

        fun <T> failure(t: Throwable): Try<T> {
            return Failure(t)
        }
    }
    abstract val throwable: Throwable?

    abstract val isSuccess: Boolean

    abstract val data: T?
}

class Success<out T>(val t: T?) : Try<T>() {

    override val isSuccess: Boolean = true

    override val data = t

    override val throwable: Throwable? = null
}

class Failure<out T>(t: Throwable) : Try<T>() {

    override val isSuccess: Boolean = false

    override val data: T? = null

    override val throwable: Throwable = t
}