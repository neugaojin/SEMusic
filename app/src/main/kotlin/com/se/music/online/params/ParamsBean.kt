package com.se.music.online.params

import java.io.Serializable

/**
 * Created by gaojin on 2018/1/14.
 * 选项参数
 */
class ParamsBean : Serializable {
    var async: Int = 0
    var cmd: Int = 0
    var sort: Int = 0
    var start: Int = 0
    var end: Int = 0
}