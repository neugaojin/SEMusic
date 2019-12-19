package com.se.music.support.utils

import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by gaojin on 2017/12/23.
 * id生成工具，防止id重复
 */
private val integer = AtomicInteger(0)

/**
 * loaderId默认从0开始，每次自增1
 *
 * @return loaderId
 */
fun generateLoaderId(): Int {
    return integer.getAndIncrement()
}

// get
val GET_MUSIC_HALL = generateLoaderId()
val GET_RECOMMEND_LIST = generateLoaderId()
val GET_SINGER_LIST = generateLoaderId()
val GET_EXPRESS_SONG = generateLoaderId()
val GET_RELATED_SONG = generateLoaderId()
val GET_SIMILAR_SONG = generateLoaderId()

// query
val QUERY_SONG_LIST = generateLoaderId()
val QUERY_LOCAL_SONG = generateLoaderId()
val QUERY_LOCAL_SINGER = generateLoaderId()
val QUERY_LOCAL_ALBUM = generateLoaderId()
val QUERY_FOLDER = generateLoaderId()
val QUERY_LOVE_SONG = generateLoaderId()