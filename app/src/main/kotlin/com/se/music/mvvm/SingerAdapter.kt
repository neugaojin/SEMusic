package com.se.music.mvvm

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.se.music.R
import com.se.music.base.picBaseUrl_300
import com.se.music.support.utils.inflate
import com.se.music.support.utils.loadUrl
import com.se.music.widget.CircleImageView

/**
 *Author: gaojin
 *Time: 2020/10/8 5:30 PM
 */

class SingerAdapter
    : PagingDataAdapter<Singer, SingerViewHolder>(SINGER_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingerViewHolder {
        return SingerViewHolder(parent.inflate(R.layout.online_singer_item))
    }

    override fun onBindViewHolder(holder: SingerViewHolder, position: Int) {
        getItem(position)?.let {
            holder.singerName.text = it.singer_name
            holder.singerAvatar.loadUrl(String.format(picBaseUrl_300, it.singer_mid), R.drawable.placeholder_disk)
        }
    }

    companion object {
        val SINGER_COMPARATOR = object : DiffUtil.ItemCallback<Singer>() {
            override fun areItemsTheSame(oldItem: Singer, newItem: Singer): Boolean =
                    oldItem.singer_id == newItem.singer_id

            override fun areContentsTheSame(oldItem: Singer, newItem: Singer): Boolean =
                    oldItem == newItem

        }
    }
}

class SingerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var singerAvatar: CircleImageView = itemView.findViewById(R.id.singer_avatar)
    var singerName: TextView = itemView.findViewById(R.id.singer_name)
}