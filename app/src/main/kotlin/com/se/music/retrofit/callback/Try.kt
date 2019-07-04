package com.se.music.retrofit.callback

/**
 * Created by gaojin on 2017/12/23.
 */
abstract class Try<out T> {

    abstract fun isSuccess(): Boolean

    abstract fun isFailure(): Boolean

    abstract fun get(): T?

    abstract fun throwable(): Throwable?

    companion object {
        fun <T> success(t: T): Try<T>? {
            return Success(t)
        }

        fun <T> failure(t: Throwable): Try<T> {
            return Failure(t)
        }
    }

    private class Success<out T>(private val t: T) : Try<T>() {

        override fun isSuccess(): Boolean {
            return true
        }

        override fun isFailure(): Boolean {
            return false
        }

        override fun get(): T {
            return t
        }

        override fun throwable(): Throwable? {
            return null
        }
    }

    private class Failure<out T>(private val throwable: Throwable) : Try<T>() {

        override fun isSuccess(): Boolean {
            return false
        }

        override fun isFailure(): Boolean {
            return true
        }

        override fun get(): T? {
            return null
        }

        override fun throwable(): Throwable {
            return throwable
        }
    }
}