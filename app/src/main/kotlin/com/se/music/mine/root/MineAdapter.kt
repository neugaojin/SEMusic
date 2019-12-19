package com.se.music.mine.root

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
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

/**
 * Author: gaojin
 * Time: 2018/5/4 下午10:50
 */
class MineAdapter constructor(private val context: Context, private val list: MutableList<SongListEntity>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mHEADER = 0X01
    private val mLIST = 0X02

    @SuppressLint("InflateParams")
    var header: View = LayoutInflater.from(context).inflate(R.layout.mine_header_view_layout, null)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == mHEADER) {
            HeadItemHolder(header)
        } else {
            ListItemHolder(parent.inflate(R.layout.mine_list_view_layout))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ListItemHolder) {
            holder.imageView.loadUrl(list[position - 1].listPic, R.drawable.placeholder_disk)
            holder.songListTitle.text = list[position - 1].name
            holder.songListInfo.text = list[position - 1].info
            holder.songListItem.setOnClickListener { Toast.makeText(context, "gj_jump", Toast.LENGTH_SHORT).show() }
        }
    }

    override fun getItemCount() = list.size + 1

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            mHEADER
        } else {
            mLIST
        }
    }

    class HeadItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class ListItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.song_list_image)
        var songListTitle: TextView = itemView.findViewById(R.id.song_list_name)
        var songListInfo: TextView = itemView.findViewById(R.id.song_list_content)
        var songListItem: View = itemView.findViewById(R.id.song_list_item)
    }
}