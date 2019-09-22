package com.se.music.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.se.music.R
import com.se.music.entity.MusicEntity
import com.se.music.service.MusicPlayer
import com.se.music.singleton.HandlerSingleton
import com.se.music.utils.inflate

/**
 * Created by gaojin on 2017/12/8.
 */
class MusicListAdapter constructor(private val context: Context, private val mList: MutableList<MusicEntity>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mHeadLayout = 0X01
    private val mContentLayout = 0X02

    private var playMusic: PlayMusic? = null
    private var handler = HandlerSingleton.INSTANCE

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ListItemViewHolder) {
            holder.onBindData(mList[position - 1])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        mHeadLayout -> CommonItemViewHolder(parent.inflate(R.layout.common_item))
        else -> ListItemViewHolder(parent.inflate(R.layout.fragment_music_song_item))
    }

    override fun getItemCount() = mList.size + 1

    override fun getItemViewType(position: Int) = when (position) {
        0 -> mHeadLayout
        else -> mContentLayout
    }

    internal inner class PlayMusic(var position: Int) : Runnable {
        /**
         * 运行在主线程
         */
        override fun run() {
            val list = LongArray(mList.size)
            val infoMap = hashMapOf<Long, MusicEntity>()
            for (i in mList.indices) {
                val info = mList[i]
                list[i] = info.audioId
                info.islocal = true
                infoMap[list[i]] = mList[i]
            }
            if (position > -1)
                MusicPlayer.playAll(infoMap, list, position)
        }
    }

    inner class CommonItemViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var select: ImageView? = null

        init {
            select = view.findViewById(R.id.select)
            view.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            if (playMusic != null)
                handler.removeCallbacks(playMusic)
            if (adapterPosition > -1) {
                playMusic = PlayMusic(0)
                handler.postDelayed(playMusic, 70)
            }
        }
    }

    inner class ListItemViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private var mMusicName: TextView = view.findViewById(R.id.music_name)
        private var mMusicInfo: TextView = view.findViewById(R.id.music_info)
        private var mAlbumInfo: TextView = view.findViewById(R.id.album_info)
        private var mListButton: ImageView = view.findViewById(R.id.viewpager_list_button)

        init {
            mListButton.setOnClickListener(this)
            view.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            if (v.id == R.id.viewpager_list_button) {
                //Empty
            } else {
                if (playMusic != null)
                    handler.removeCallbacks(playMusic)
                if (adapterPosition > -1) {
                    playMusic = PlayMusic(adapterPosition - 1)
                    handler.postDelayed(playMusic, 70)
                }
            }
        }

        fun onBindData(musicEntity: MusicEntity) {
            mMusicName.text = musicEntity.musicName
            mMusicInfo.text = musicEntity.artist
            mAlbumInfo.text = musicEntity.albumName
        }
    }
}