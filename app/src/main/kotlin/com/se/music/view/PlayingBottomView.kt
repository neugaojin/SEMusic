package com.se.music.view

import android.content.ContentValues
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSeekBar
import com.se.music.R
import com.se.music.provider.database.entity.MusicInfoCache
import com.se.music.provider.metadata.LOVE_SONG_CONTENT_URI
import com.se.music.service.MediaService
import com.se.music.service.MusicPlayer
import com.se.music.utils.manager.GlobalPlayTimeManager
import com.se.music.utils.manager.PlayTimeListener
import com.se.music.utils.ms2Minute

/**
 *Author: gaojin
 *Time: 2018/9/27 下午5:37
 * 播放音乐底部操作区域
 */
class PlayingBottomView @JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr), View.OnClickListener, SeekBar.OnSeekBarChangeListener, ViewBlockAction, PlayTimeListener {

    private lateinit var repeatMode: ImageView
    private lateinit var preSong: ImageView
    private lateinit var centerControl: ImageView
    private lateinit var nextSong: ImageView
    private lateinit var songList: ImageView
    private lateinit var favorite: ImageView
    private lateinit var download: ImageView
    private lateinit var share: ImageView
    private lateinit var comment: ImageView

    private lateinit var seekTimePlayed: TextView
    private lateinit var seekTotalTime: TextView
    private lateinit var seekBar: AppCompatSeekBar

    private var duration: Long = 0

    init {
        init(context)
    }

    private fun init(context: Context) {
        orientation = VERTICAL
        View.inflate(context, R.layout.block_playing_bottom, this)

        repeatMode = findViewById(R.id.repeat_mode)
        preSong = findViewById(R.id.pre_song)
        centerControl = findViewById(R.id.center_control)
        nextSong = findViewById(R.id.next_song)
        songList = findViewById(R.id.song_list)

        favorite = findViewById(R.id.playing_favorite)
        download = findViewById(R.id.playing_download)
        share = findViewById(R.id.playing_share)
        comment = findViewById(R.id.playing_comment)

        seekTimePlayed = findViewById(R.id.seek_current_time)
        seekTotalTime = findViewById(R.id.seek_all_time)
        seekBar = findViewById(R.id.player_seek_bar)

        seekBar.isIndeterminate = false
        seekBar.max = 1000
        seekBar.progress = 1

        seekBar.setOnSeekBarChangeListener(this)
        repeatMode.setOnClickListener(this)
        preSong.setOnClickListener(this)
        centerControl.setOnClickListener(this)
        nextSong.setOnClickListener(this)
        songList.setOnClickListener(this)
        favorite.setOnClickListener(this)
        download.setOnClickListener(this)
        share.setOnClickListener(this)
        comment.setOnClickListener(this)

        GlobalPlayTimeManager.registerListener(this)
    }

    /**
     * 更新模块
     */
    override fun updateBlock() {
        updateRepeatStatus()
        updatePlayingStatus()
        setSeekBar()
    }

    /**
     * 切换歌曲
     */
    override fun musicChanged() {
        setSeekBar()
        updatePlayingStatus()
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        seekTimePlayed.text = progress.toLong().ms2Minute()
        val i = progress * MusicPlayer.duration() / 1000
        if (fromUser) {
            MusicPlayer.seek(i)
            seekTimePlayed.text = i.ms2Minute()
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    override fun playedTime(position: Long, duration: Long) {
        seekBar.progress = (1000 * position / duration).toInt()
        seekTimePlayed.text = position.ms2Minute()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.repeat_mode -> {
                setRepeatStatus()
                updateRepeatStatus()
            }
            R.id.pre_song -> {
                MusicPlayer.previous()
            }
            R.id.center_control -> {
                MusicPlayer.playOrPause()
                updatePlayingStatus()
            }
            R.id.next_song -> MusicPlayer.nextPlay()
            R.id.playing_favorite -> {
                val values = ContentValues()
                values.run {
                    put(MusicInfoCache.SONG_ID, MusicPlayer.getAudioId())
                    put(MusicInfoCache.NAME, MusicPlayer.getTrackName())
                    put(MusicInfoCache.ALBUM_ID, MusicPlayer.getAlbumId())
                    put(MusicInfoCache.ALBUM_NAME, MusicPlayer.getAlbumName())
                    put(MusicInfoCache.ALBUM_PIC, MusicPlayer.getAlbumPic())
                    put(MusicInfoCache.ARTIST_NAME, MusicPlayer.getArtistName())
                    put(MusicInfoCache.DURATION, MusicPlayer.duration())
                    if (MusicPlayer.isTrackLocal()) {
                        put(MusicInfoCache.IS_LOCAL, 0)
                    } else {
                        put(MusicInfoCache.IS_LOCAL, 1)
                    }
                }
                context.contentResolver.insert(LOVE_SONG_CONTENT_URI, values)
            }
        }
    }

    private fun updatePlayingStatus() {
        if (MusicPlayer.isPlaying()) {
            centerControl.setImageResource(R.drawable.default_player_btn_pause_selector)
        } else {
            centerControl.setImageResource(R.drawable.default_player_btn_play_selector)
        }
    }

    private fun setRepeatStatus() {
        when (MusicPlayer.getRepeatMode()) {
            MediaService.REPEAT_ALL -> MusicPlayer.setRepeatMode(MediaService.REPEAT_CURRENT)
            MediaService.REPEAT_CURRENT -> MusicPlayer.setRepeatMode(MediaService.REPEAT_SHUFFLER)
            MediaService.REPEAT_SHUFFLER -> MusicPlayer.setRepeatMode(MediaService.REPEAT_ALL)
        }
    }

    private fun updateRepeatStatus() {
        when (MusicPlayer.getRepeatMode()) {
            MediaService.REPEAT_CURRENT -> repeatMode.setImageResource(R.drawable.player_btn_repeat_once)
            MediaService.REPEAT_ALL -> repeatMode.setImageResource(R.drawable.player_btn_repeat)
            MediaService.REPEAT_SHUFFLER -> repeatMode.setImageResource(R.drawable.player_btn_random_normal)
        }
    }

    private fun setSeekBar() {
        duration = MusicPlayer.duration()
        seekTotalTime.text = duration.ms2Minute()
        seekTimePlayed.text = MusicPlayer.position().ms2Minute()
        if (duration != 0.toLong()) {
            seekBar.progress = (1000 * MusicPlayer.position() / duration).toInt()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        GlobalPlayTimeManager.unregisterListener(this)
    }
}