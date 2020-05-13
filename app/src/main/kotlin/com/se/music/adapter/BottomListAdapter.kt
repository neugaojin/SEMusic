package com.se.music.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.media.session.MediaSessionCompat
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.se.music.R
import com.se.music.base.Null
import com.se.music.support.utils.inflate
import com.se.music.uamp.MediaItemData

/**
 *Author: gaojin
 *Time: 2020/4/30 4:18 PM
 */

class BottomListAdapter(private val context: Context) : RecyclerView.Adapter<BottomListAdapter.BottomListItemViewHolder>() {

    var nowPlayingMediaId = Null
    var list: List<MediaItemData>? = null

    private var itemClick: ((Int, MediaItemData?) -> Unit)? = null
    private var itemDelete: ((Int, MediaItemData?) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BottomListItemViewHolder(parent.inflate(R.layout.bottom_list_item_layout))

    override fun getItemCount() = list?.size ?: 0

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BottomListItemViewHolder, position: Int) {
        val item = list?.get(position) ?: return
        holder.apply {
            songName.text = "${position + 1}.  ${item.title}"
            artistName.text = item.artist
            if (item.mediaId == nowPlayingMediaId) {
                songName.setTextColor(ContextCompat.getColor(context, R.color.medium_sea_green))
                artistName.setTextColor(ContextCompat.getColor(context, R.color.medium_sea_green))
                line.setBackgroundColor(ContextCompat.getColor(context, R.color.medium_sea_green))
            } else {
                songName.setTextColor(ContextCompat.getColor(context, R.color.light_black))
                artistName.setTextColor(ContextCompat.getColor(context, R.color.light_black))
                line.setBackgroundColor(ContextCompat.getColor(context, R.color.gray))
            }
        }
    }

    fun setOnItemClickListener(listener: (Int, MediaItemData?) -> Unit) {
        itemClick = listener
    }

    fun setOnItemDeleteListener(listener: (Int, MediaItemData?) -> Unit) {
        itemDelete = listener
    }

    inner class BottomListItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val songName: TextView = view.findViewById(R.id.content_item_song)
        val artistName: TextView = view.findViewById(R.id.content_item_artist)
        val line: View = view.findViewById(R.id.content_divide_line)

        init {
            view.findViewById<ImageView>(R.id.bottom_item_delete).setOnClickListener {
                itemDelete?.invoke(adapterPosition, list?.get(adapterPosition))
            }
            view.setOnClickListener {
                itemClick?.invoke(adapterPosition, list?.get(adapterPosition))
            }
        }

    }
}