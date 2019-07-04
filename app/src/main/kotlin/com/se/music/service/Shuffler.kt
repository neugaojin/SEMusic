package com.se.music.service

import java.util.*

/**
 *Author: gaojin
 *Time: 2018/9/16 下午7:46
 *产生随机数
 */

class Shuffler {

    companion object {
        val instance: Shuffler by lazy { Shuffler() }
    }

    private val historyOfNumbers = LinkedList<Int>()
    private val previousNumbers = TreeSet<Int>()
    private val random = Random()
    /**
     * 上一次nextInt产生过的数字
     */
    private var previous: Int = 0

    /**
     * 产生一个未产生过的整数
     */
    fun nextInt(interval: Int): Int {
        var next: Int
        do {
            next = random.nextInt(interval)
        } while (next == previous &&
                interval > 1 &&
                !previousNumbers.contains(next))

        previous = next
        historyOfNumbers.add(previous)
        previousNumbers.add(previous)
        cleanUpHistory()
        return next
    }

    private fun cleanUpHistory() {
        if (historyOfNumbers.isNotEmpty() && historyOfNumbers.size >= MediaService.MAX_HISTORY_SIZE) {
            for (i in 0 until MediaService.MAX_HISTORY_SIZE / 2) {
                previousNumbers.remove(historyOfNumbers.removeFirst())
            }
        }
    }
}