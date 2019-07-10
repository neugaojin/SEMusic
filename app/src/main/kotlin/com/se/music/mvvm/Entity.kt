package com.se.music.mvvm

/**
 *Author: gaojin
 *Time: 2019-07-05 16:28
 */

data class SingerEntity(val code: Int = 0,
                        val data: Data? = null,
                        val message: String? = null,
                        val subcode: Int = 0)

data class Data(val per_page: Int = 0,
                val total: Int = 0,
                val total_page: Int = 0,
                val list: List<Singer>? = null)

data class Singer(
        val Farea: String? = null,
        val Fattribute_3: String? = null,
        val Fattribute_4: String? = null,
        val Fgenre: String? = null,
        val Findex: String? = null,
        val Fother_name: String? = null,
        val Fsinger_id: String? = null,
        val Fsinger_mid: String? = null,
        val Fsinger_name: String? = null,
        val Fsinger_tag: String? = null,
        val Fsort: String? = null,
        val Ftrend: String? = null,
        val Ftype: String? = null,
        val voc: String? = null
)