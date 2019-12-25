package com.se.music.support.database.database.recent

/**
 * Author: gaojin
 * Time: 2018/5/6 下午5:42
 */
class Song {
    var albumId: Long
    var albumName: String
    var artistId: Long
    var artistName: String
    var duration: Int
    var id: Long
    var title: String
    var trackNumber: Int

    constructor() {
        this.id = -1
        this.albumId = -1
        this.artistId = -1
        this.title = ""
        this.artistName = ""
        this.albumName = ""
        this.duration = -1
        this.trackNumber = -1
    }

    constructor(_id: Long, _albumId: Long, _artistId: Long, _title: String, _artistName: String, _albumName: String, _duration: Int, _trackNumber: Int) {
        this.id = _id
        this.albumId = _albumId
        this.artistId = _artistId
        this.title = _title
        this.artistName = _artistName
        this.albumName = _albumName
        this.duration = _duration
        this.trackNumber = _trackNumber
    }
}