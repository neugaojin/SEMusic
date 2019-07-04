package com.se.music.entity

import com.google.gson.JsonElement
import com.se.music.retrofit.base.ConvertData
import com.se.music.singleton.GsonFactory

/**
 *Author: gaojin
 *Time: 2018/7/9 下午11:30
 */

class Album : ConvertData<Album?> {
    override fun convertData(jsonElement: JsonElement): Album? {
        val root = jsonElement.asJsonObject
        if (!root.has("album")) {
            return null
        }
        return GsonFactory.instance.fromJson(root.get("album"), Album::class.java)
    }

    var name: String? = null
    var artist: String? = null
    var mbid: String? = null
    var url: String? = null
    var listeners: String? = null
    var playcount: String? = null
    var tracks: TracksBean? = null
    var tags: TagsBean? = null
    var image: List<ImageBean>? = null

    class TracksBean {
        var track: List<*>? = null
    }

    class TagsBean {
        var tag: List<*>? = null
    }
}