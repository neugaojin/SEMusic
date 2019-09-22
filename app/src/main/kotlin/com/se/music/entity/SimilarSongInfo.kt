package com.se.music.entity

import com.google.gson.JsonElement
import com.se.music.base.Null
import com.se.senet.base.GsonFactory

/**
 *Author: gaojin
 *Time: 2018/10/16 上午12:20
 */

class SimilarSongInfo : com.se.senet.base.ConvertData<SimilarSongInfo?> {

    override fun convertData(jsonElement: JsonElement): SimilarSongInfo? {
        val root = jsonElement.asJsonObject
        if (!root.has("similartracks")) {
            return null
        }
        return GsonFactory.INSTANCE.fromJson(root.get("similartracks"), SimilarSongInfo::class.java)
    }

    var track: List<SimilarTrackBean>? = null

    class SimilarTrackBean {
        var name: String? = null
        var playcount: Int = 0
        var mbid: String? = null
        var match: Int = 0
        var url: String? = null
        var duration: Int = 0
        var artist: ArtistBean? = null
        var image: List<ImageBean>? = null

        class ArtistBean {
            var name: String = Null
            var mbid: String? = null
            var url: String? = null
        }
    }
}