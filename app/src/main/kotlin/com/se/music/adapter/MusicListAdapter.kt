package com.se.music.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.se.music.R
import com.se.music.support.utils.inflate
import com.se.music.uamp.MediaItemData

/**
 * Created by gaojin on 2017/12/8.
 */
class MusicListAdapter()
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mHeadLayout = 0X01
    private val mContentLayout = 0X02
    private val mList = mutableListOf<MediaItemData>()
    private var listener: OnItemClickListener? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ListItem) {
            holder.onBindData(mList[position - 1])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        mHeadLayout -> HeadItem(parent.inflate(R.layout.common_item))
        else -> ListItem(parent.inflate(R.layout.fragment_music_song_item))
    }

    override fun getItemCount() = mList.size + 1

    override fun getItemViewType(position: Int) = when (position) {
        0 -> mHeadLayout
        else -> mContentLayout
    }

    fun setData(data: List<MediaItemData>) {
        mList.clear()
        mList.addAll(data)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class HeadItem(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View) {
        }
    }

    inner class ListItem(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

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
                listener?.onItemClick(mList[adapterPosition - 1])
            }
        }

        fun onBindData(musicEntity: MediaItemData) {
            mMusicName.text = musicEntity.title
            mMusicInfo.text = musicEntity.artist
            mAlbumInfo.text = musicEntity.album
        }
    }

    interface OnItemClickListener {
        fun onItemClick(mediaItem: MediaItemData)
    }
}