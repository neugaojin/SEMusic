package com.se.music.entity

import android.os.Parcelable
import com.se.music.base.Null
import kotlinx.android.parcel.Parcelize

/**
 *Author: gaojin
 *Time: 2018/5/26 下午9:35
 * Common Entity 集合
 */

/**
 * 专辑bean
 */
data class AlbumEntity(
    var albumId: Long = -1,
    var albumName: String,
    var numberOfSongs: Int,
    var albumArtist: String,
    var albumKey: String
) {
    var imageUrl: String = Null
}

/**
 * 歌手bean
 */
data class ArtistEntity(
    var artistName: String,
    var numberOfTracks: Int,
    var artistId: Int,
    var artistKey: String
) {
    var imageId: String = Null
}

@Parcelize
data class MusicEntity(
    var audioId: Long = -1,
    var musicName: String,
    var artist: String,
    var albumData: String?,
    var albumKey: String,
    var duration: Long,
    var albumName: String?,
    var artistId: Long,
    var data: String?,
    var size: Long,
    var folder: String?,
    var lrc: String?,
    var sort: String?,
        // 0表示没有收藏 1表示收藏,
    var favorite: Int = 0,
    var islocal: Boolean
) : Parcelable

data class SongListEntity(
    var id: String,
    var name: String,
    var createTime: String
) {
    var count: Int = 0
    var creator: String? = null
    var listPic: String? = null
    var info: String? = null
}

data class OverFlowItem(
        // 信息标题
    var title: String,
        // 图片ID
    var avatar: Int = 0
)