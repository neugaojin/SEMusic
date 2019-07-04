package com.se.music.online.params

import java.io.Serializable

/**
 * Created by gaojin on 2018/1/28.
 */
class ExpressPostParams : Serializable {
    var comm: Common = Common()
    var new_album: CategoryBean = CategoryBean()
    var new_song: CategoryBean = CategoryBean()
}