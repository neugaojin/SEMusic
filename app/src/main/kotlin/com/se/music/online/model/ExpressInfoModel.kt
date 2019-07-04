package com.se.music.online.model

import java.io.Serializable

/**
 * Created by gaojin on 2018/1/28.
 */
class ExpressInfoModel : Serializable {
    var new_song: NewSong? = null
    var new_album: NewAlbum? = null
    var code: Int = 0
    var ts: Long = 0
}