package com.se.music.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.se.music.R
import com.se.music.base.data.database.entity.ArtistEntity
import com.se.music.support.utils.*

/**
 *Author: gaojin
 *Time: 2018/7/5 下午9:23
 */

class SingerListAdapter constructor(
        private val context: Context,
        private val list: MutableList<ArtistEntity>
) : RecyclerView.Adapter<SingerListAdapter.SingerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingerViewHolder {
        return SingerViewHolder(parent.inflate(R.layout.mine_local_singer_item))
    }

    override fun getItemCount() = list.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SingerViewHolder, position: Int) {
        val artistEntity = list[position]
        holder.singerName.text = artistEntity.artistName
        holder.singerSongCount.text = "${artistEntity.numberOfTracks}首"
        if (artistEntity.imageId.isEmpty()) {
//            loaderManager.initLoader(generateLoaderId(), null, buildArtistCallBacks(holder, position))
        } else {
            holder.singerAvatar.loadUrl(artistEntity.imageId.getMediumImageUrl(), R.drawable.default_singer_avatar)
        }
    }

    class SingerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val singerAvatar: ImageView = view.findViewById(R.id.local_singer_avatar)
        val singerName: TextView = view.findViewById(R.id.local_singer_name)
        val singerSongCount: TextView = view.findViewById(R.id.local_singer_number_of_tracks)
    }
}