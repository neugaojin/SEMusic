package com.se.music.scene.mine

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.se.music.R
import com.se.music.entity.SongListEntity
import com.se.music.support.utils.inflate
import com.se.music.support.utils.loadUrl
import com.se.music.widget.adapter.HeadFootAdapter

/**
 *Author: gaojin
 *Time: 2019-12-02 20:12
 */

class MineAdapterV2(context: Context, list: MutableList<SongListEntity>) : HeadFootAdapter<SongListEntity, ListItemHolder>(context, list) {

    override fun createItem(parent: ViewGroup, viewType: Int): ListItemHolder {
        return ListItemHolder(parent.inflate(R.layout.mine_list_view_layout))
    }

    override fun bindItem(holder: ListItemHolder, position: Int) {
        holder.imageView.loadUrl(data[position].listPic, R.drawable.placeholder_disk)
        holder.songListTitle.text = data[position].name
        holder.songListInfo.text = data[position].info
        holder.songListItem.setOnClickListener { Toast.makeText(context, "gj_jump", Toast.LENGTH_SHORT).show() }
    }
}

class ListItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var imageView: ImageView = itemView.findViewById(R.id.song_list_image)
    var songListTitle: TextView = itemView.findViewById(R.id.song_list_name)
    var songListInfo: TextView = itemView.findViewById(R.id.song_list_content)
    var songListItem: View = itemView.findViewById(R.id.song_list_item)
}