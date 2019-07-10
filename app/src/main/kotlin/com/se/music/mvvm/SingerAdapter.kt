package com.se.music.mvvm

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.se.music.R
import com.se.music.base.picBaseUrl_300
import com.se.music.utils.inflate
import com.se.music.utils.loadUrl
import com.se.music.widget.CircleImageView

/**
 *Author: gaojin
 *Time: 2019-07-05 17:16
 */

class SingerAdapter(private val singerList: List<Singer>) : RecyclerView.Adapter<ItemViewHolder>() {
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.singerName.text = singerList[position].Fsinger_name
        holder.singerAvatar.loadUrl(String.format(picBaseUrl_300, singerList[position].Fsinger_mid))
    }

    override fun getItemCount(): Int {
        return singerList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(parent.inflate(R.layout.online_singer_item))
    }
}

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var singerAvatar: CircleImageView = itemView.findViewById(R.id.singer_avatar)
    var singerName: TextView = itemView.findViewById(R.id.singer_name)
}