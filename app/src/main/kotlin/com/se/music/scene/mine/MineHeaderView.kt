package com.se.music.scene.mine

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.se.music.R
import com.se.music.base.BaseConfig

/**
 *Author: gaojin
 *Time: 2019-12-03 11:16
 */

class MineHeaderView @JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr, defStyleRes), LifecycleOwner, View.OnClickListener {

    private lateinit var gridLayout: GridLayout

    init {
        init()
    }

    override fun getLifecycle(): Lifecycle {
        return (context as AppCompatActivity).lifecycle
    }

    private fun init() {
        orientation = VERTICAL
        View.inflate(context, R.layout.mine_header_view_layout_v2, this)
        gridLayout = findViewById(R.id.mine_func_grid)
        addViewToGridLayout(gridLayout)
        val dataList = listOf(DataHolder(context.resources.getString(R.string.mine_local_music), R.drawable.ic_my_music_local_song, R.id.local_music)
                , DataHolder(context.resources.getString(R.string.mine_down_music), R.drawable.ic_my_music_download_song, R.id.download_music)
                , DataHolder(context.resources.getString(R.string.mine_recent_music), R.drawable.ic_my_music_recent_playlist, R.id.recent_music)
                , DataHolder(context.resources.getString(R.string.mine_love_music), R.drawable.ic_my_music_my_favorite, R.id.love_music)
                , DataHolder(context.resources.getString(R.string.mine_buy_music), R.drawable.ic_my_music_paid_songs, R.id.buy_music)
                , DataHolder(context.resources.getString(R.string.mine_running_radio), R.drawable.ic_my_music_running_radio, R.id.running_radio))
        initGridView(dataList)
    }

    private fun initGridView(list: List<DataHolder>) {
        list.forEachIndexed { index, dataHolder ->
            val itemView = gridLayout.getChildAt(index)
            val imageView: ImageView = itemView.findViewById(R.id.img_item)
            val itemName: TextView = itemView.findViewById(R.id.tv_item_name)
            itemView.id = dataHolder.id
            itemView.setOnClickListener(this)
            itemName.text = dataHolder.itemName
            imageView.setImageDrawable(context.getDrawable(dataHolder.drawablePic))
        }
    }

    fun update(list: List<String>) {
        list.forEachIndexed { index, info ->
            val itemView = gridLayout.getChildAt(index)
            val itemCount: TextView = itemView.findViewById(R.id.tv_item_count)
            itemCount.text = info
        }
    }

    private fun addViewToGridLayout(container: ViewGroup) {
        for (i in 0..5) {
            val itemView = LayoutInflater.from(context).inflate(R.layout.view_mine_item_view, container, false)
            val params: GridLayout.LayoutParams = itemView.layoutParams as GridLayout.LayoutParams
            params.width = BaseConfig.width / 3
            itemView.layoutParams = params
            container.addView(itemView)
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.local_music -> localMusic()
            R.id.download_music -> downloadMusic()
            R.id.recent_music -> recentMusic()
            R.id.love_music -> loveMusic()
            R.id.running_radio -> runningRadio()
            R.id.buy_music -> buyMusic()
        }
    }

    private fun localMusic() {
    }

    private fun downloadMusic() {
    }

    private fun recentMusic() {
    }

    private fun loveMusic() {
    }

    private fun runningRadio() {
    }

    private fun buyMusic() {
        Toast.makeText(context, "测试", Toast.LENGTH_SHORT).show()
    }
}

class DataHolder(
        var itemName: String,
        var drawablePic: Int,
        var id: Int
)