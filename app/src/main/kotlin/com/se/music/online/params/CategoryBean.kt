package com.se.music.online.params

import java.io.Serializable

/**
 * Created by gaojin on 2018/1/14.
 */
class CategoryBean : Serializable {
    var method: String? = null
    var param: ParamsBean = ParamsBean()
    var module: String? = null
}