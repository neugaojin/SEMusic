package com.se.music.base.log

/**
 *Author: gaojin
 *Time: 2019-12-05 20:58
 */
@Suppress("NOTHING_TO_INLINE")
internal inline fun (() -> Any?).toStringSafe(): String {
    return try {
        invoke().toString()
    } catch (e: Exception) {
        "Log message invocation failed: $e"
    }
}