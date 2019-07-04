package com.se.music.online.model

/**
 * Created by gaojin on 2018/1/1.
 */
class RecommendListModel {
    var recomPlaylist: RecomPlaylistBean? = null
    var code: Int = 0
    var ts: Long = 0

    inner class RecomPlaylistBean {
        var data: DataBean? = null
        var code: Int = 0
    }

    inner class DataBean {
        var v_hot: List<VHotBean>? = null
    }

    inner class VHotBean {
        var album_pic_mid: String? = null
        var content_id: Long = 0
        var cover: String? = null
        var creator: Long = 0
        var edge_mark: String? = null
        var id: Int = 0
        var is_dj: Boolean = false
        var is_vip: Boolean = false
        var jump_url: String? = null
        var listen_num: Int = 0
        var pic_mid: String? = null
        var rcmdcontent: String? = null
        var rcmdtemplate: String? = null
        var rcmdtype: Int = 0
        var singerid: Int = 0
        var title: String? = null
        var tjreport: String? = null
        var type: Int = 0
        var username: String? = null
    }
}