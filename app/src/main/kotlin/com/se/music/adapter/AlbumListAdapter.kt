package com.se.music.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.se.music.R
import com.se.music.base.data.database.entity.AlbumEntity
import com.se.music.base.data.database.provider.ImageStore
import com.se.music.support.utils.*
import java.util.*

/**
 *Author: gaojin
 *Time: 2018/7/9 下午8:17
 */

class AlbumListAdapter constructor(
        private val context: Context,
        private val list: ArrayList<AlbumEntity>
) : RecyclerView.Adapter<AlbumListAdapter.AlbumViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AlbumViewHolder(parent.inflate(R.layout.mine_local_album_item))

    override fun getItemCount() = list.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val albumEntity = list[position]
        holder.albumName.text = albumEntity.albumName
        holder.albumSongCount.text = "${albumEntity.numberOfSongs}首"

        val imageId = ImageStore.instance.query(albumEntity.albumName.hashCode())
        if (imageId?.isEmpty() != false) {
//            loaderManager.initLoader(generateLoaderId(), null, buildAlbumCallBacks(context, holder.albumPic, albumEntity))
        } else {
            holder.albumPic.loadUrl(imageId.getMegaImageUrl(), R.drawable.default_album_avatar)
        }
    }

    class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val albumPic: ImageView = view.findViewById(R.id.local_album_pic)
        val albumName: TextView = view.findViewById(R.id.local_album_name)
        val albumSongCount: TextView = view.findViewById(R.id.local_album_number_of_tracks)
    }
}