package com.se.music.online.model

/**
 * Created by gaojin on 2017/12/23.
 */
class HallModel {
    var code: Int = 0
    var data: Data? = null

    class Data {
        var slider: List<Slider>? = null
        var radioList: List<RadioList>? = null

        class Slider {
            var linkUrl: String? = null
            var picUrl: String? = null
            var id: Int = 0
        }

        class RadioList {
            var picUrl: String? = null
            var ftitle: String? = null
            var radioid: Int = 0
        }
    }
}