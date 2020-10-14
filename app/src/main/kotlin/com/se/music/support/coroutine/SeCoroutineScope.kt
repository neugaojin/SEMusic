package com.se.music.support.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

/**
 *Author: gaojin
 *Time: 2019-12-25 18:18
 */

class SeCoroutineScope : CoroutineScope, Closeable {
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun close() {
        job.cancel()
    }
}