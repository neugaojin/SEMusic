package com.se.music.entity

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import com.se.music.retrofit.base.ConvertData
import com.se.music.singleton.GsonFactory

/**
 *Author: gaojin
 *Time: 2018/7/3 下午7:50
 */

class Artist : ConvertData<Artist?> {
    override fun convertData(jsonElement: JsonElement): Artist? {
        val rootObject = jsonElement.asJsonObject
        if (!rootObject.has("artist")) {
            return null
        }
        return GsonFactory.instance.fromJson(rootObject.get("artist"), Artist::class.java)
    }

    var name: String? = null
    var mbid: String? = null
    var url: String? = null
    var streamable: String? = null
    var ontour: String? = null
    var stats: StatsBean? = null
    var similar: SimilarBean? = null
    var tags: TagsBean? = null
    var bio: BioBean? = null
    var image: List<ImageBean>? = null

    class StatsBean {
        var listeners: String? = null
        var playcount: String? = null
    }

    class SimilarBean {
        var artist: List<*>? = null
    }

    class TagsBean {
        var tag: List<TagBean>? = null

        class TagBean {
            var name: String? = null
            var url: String? = null
        }
    }

    class BioBean {
        var links: LinksBean? = null
        var published: String? = null
        var summary: String? = null
        var content: String? = null

        class LinksBean {
            var link: LinkBean? = null

            class LinkBean {
                @SerializedName("#text")
                var `_$Text94`: String? = null // FIXME check this code
                var rel: String? = null
                var href: String? = null
            }
        }
    }
}