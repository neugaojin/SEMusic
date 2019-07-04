package com.se.music.online.params

import java.io.Serializable

/**
 * Created by gaojin on 2018/1/1.
 */
class CommonPostParams : Serializable {
    var comm: Common = Common()
    var recomPlaylist: CategoryBean = CategoryBean()
}