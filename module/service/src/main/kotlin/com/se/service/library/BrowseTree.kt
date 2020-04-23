package com.se.service.library

import android.content.Context
import android.support.v4.media.MediaMetadataCompat
import com.se.service.data.MusicSource

/**
 *Author: gaojin
 *Time: 2020/4/16 5:08 PM
 */

class BrowseTree(context: Context, musicSource: MusicSource) {
    private val mediaIdToChildren = mutableMapOf<String, MutableList<MediaMetadataCompat>>()
}