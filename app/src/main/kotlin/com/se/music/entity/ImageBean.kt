package com.se.music.entity

import com.google.gson.annotations.SerializedName
import com.se.music.base.Null

/**
 *Author: gaojin
 *Time: 2018/10/14 下午9:14
 */

class ImageBean {
    @SerializedName("#text")
    var imgUrl: String = Null
    var size: String = Null
}