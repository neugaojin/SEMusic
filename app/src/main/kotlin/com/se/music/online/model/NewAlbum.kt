package com.se.music.online.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by gaojin on 2018/1/28.
 */
class NewAlbum : Serializable {
    var data: DataBean? = null
    var code: Int = 0

    class DataBean {
        var size: Int = 0
        var type: Int = 0
        var song_list: List<SongListBean>? = null
        var type_info: List<TypeInfoBean>? = null

        class SongListBean {
            var action: ActionBean? = null
            var album: AlbumBean? = null
            var bpm: Int = 0
            var data_type: Int = 0
            var file: FileBean? = null
            var fnote: Int = 0
            var genre: Int = 0
            var id: Int = 0
            var index_album: Int = 0
            var index_cd: Int = 0
            var interval: Int = 0
            var isonly: Int = 0
            var ksong: KsongBean? = null
            var label: String? = null
            var language: Int = 0
            var mid: String? = null
            var modify_stamp: Int = 0
            var mv: MvBean? = null
            var name: String? = null
            var pay: PayBean? = null
            var status: Int = 0
            var subtitle: String? = null
            var time_public: String? = null
            var title: String? = null
            var trace: String? = null
            var type: Int = 0
            var url: String? = null
            var version: Int = 0
            var volume: VolumeBean? = null
            var singer: List<SingerBean>? = null

            class ActionBean {
                var alert: Int = 0
                var icons: Int = 0
                var msgdown: Int = 0
                var msgfav: Int = 0
                var msgid: Int = 0
                var msgpay: Int = 0
                var msgshare: Int = 0
                @SerializedName("switch")
                var switchX: Int = 0
            }

            class AlbumBean {

                var id: Int = 0
                var mid: String? = null
                var name: String? = null
                var subtitle: String? = null
                var time_public: String? = null
                var title: String? = null
            }

            class FileBean {

                var media_mid: String? = null
                var size_128mp3: Int = 0
                var size_192aac: Int = 0
                var size_192ogg: Int = 0
                var size_24aac: Int = 0
                var size_320mp3: Int = 0
                var size_48aac: Int = 0
                var size_96aac: Int = 0
                var size_ape: Int = 0
                var size_dts: Int = 0
                var size_flac: Int = 0
                var size_try: Int = 0
                var try_begin: Int = 0
                var try_end: Int = 0
            }

            class KsongBean {
                var id: Int = 0
                var mid: String? = null
            }

            class MvBean {
                var id: Int = 0
                var name: String? = null
                var title: String? = null
                var vid: String? = null
            }

            class PayBean {
                var pay_down: Int = 0
                var pay_month: Int = 0
                var pay_play: Int = 0
                var pay_status: Int = 0
                var price_album: Int = 0
                var price_track: Int = 0
                var time_free: Int = 0
            }

            class VolumeBean {
                var gain: Double = 0.toDouble()
                var lra: Double = 0.toDouble()
                var peak: Double = 0.toDouble()
            }

            class SingerBean {

                var id: Int = 0
                var mid: String? = null
                var name: String? = null
                var title: String? = null
                var type: Int = 0
                var uin: Int = 0
            }
        }

        class TypeInfoBean {
            var id: Int = 0
            var report: String? = null
            var title: String? = null
        }
    }
}