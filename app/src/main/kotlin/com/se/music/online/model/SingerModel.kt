package com.se.music.online.model

/**
 * Created by gaojin on 2018/1/3.
 */
class SingerModel {

    var code: Int = 0
    var data: Data? = null
    var message: String? = null
    var subcode: Int = 0

    class Data {
        var per_page: Int = 0
        var total: Int = 0
        var total_page: Int = 0
        var list: List<Singer>? = null

        class Singer {
            var Farea: String? = null
            var Fattribute_3: String? = null
            var Fattribute_4: String? = null
            var Fgenre: String? = null
            var Findex: String? = null
            var Fother_name: String? = null
            var Fsinger_id: String? = null
            var Fsinger_mid: String? = null
            var Fsinger_name: String? = null
            var Fsinger_tag: String? = null
            var Fsort: String? = null
            var Ftrend: String? = null
            var Ftype: String? = null
            var voc: String? = null
        }
    }
}