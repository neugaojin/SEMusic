package com.se.music.service

/**
 *Author: gaojin
 *Time: 2018/9/2 下午3:44
 */

/**
 * 暂停or播放状态变化
 */
const val PLAY_STATE_CHANGED = "com.se.music.play_state_changed"
const val POSITION_CHANGED = "com.se.music.positionchanged"
const val SEND_PROGRESS = "com.se.music.progress"
const val META_CHANGED = "com.se.music.meta_changed"
const val MUSIC_CHANGED = "com.se.music.change_music"
const val QUEUE_CHANGED = "com.se.music.queuechanged"
const val TRACK_ERROR = "com.se.music.trackerror"
const val TRACK_PREPARED = "com.se.music.prepared"
const val REFRESH = "com.se.music.refresh"
const val PREVIOUS_FORCE_ACTION = "com.se.music.previous.force"
const val PREVIOUS_ACTION = "com.se.music.previous"
const val REPEAT_MODE_CHANGED = "com.se.music.repeatmodechanged"
const val LRC_UPDATED = "com.se.music.updatelrc"
const val TRY_GET_TRACK_INFO = "com.se.music.gettrackinfo"
const val TIMBER_PACKAGE_NAME = "com.se.music"
const val MUSIC_PACKAGE_NAME = "com.android.music"
const val TOGGLE_PAUSE_ACTION = "com.se.music.togglepause"
const val NEXT_ACTION = "com.se.music.next"
const val STOP_ACTION = "com.se.music.stop"
const val BUFFER_UP = "com.se.music.bufferup"
const val MUSIC_LOADING = "com.se.music.loading"
const val REPEAT_ACTION = "com.se.music.repeat"
const val SHUFFLE_ACTION = "com.se.music.shuffle"
const val MUSIC_COUNT_CHANGED = "com.se.music.musiccountchanged"
const val PLAYLIST_COUNT_CHANGED = "com.se.music.playlistcountchanged"
const val EMPTY_LIST = "com.se.music.emptyplaylist"
const val SHUTDOWN = "com.se.music.shutdown"