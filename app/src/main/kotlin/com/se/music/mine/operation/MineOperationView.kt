package com.se.music.mine.operation

import android.annotation.SuppressLint
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Keep
import com.se.music.R
import com.se.music.base.BaseConfig
import com.se.music.fragment.*
import com.se.music.utils.startFragment

/**
 * Author: gaojin
 * Time: 2018/5/7 下午4:22
 */
class MineOperationView(presenter: com.se.router.mvp.MvpPresenter, viewId: Int, view: View) : com.se.router.mvp.BaseView(presenter, viewId), View.OnClickListener {

    private lateinit var rootView: GridLayout

    companion object {
        const val preFragmentTag = MainFragment.TAG
    }

    init {
        initView(view)
    }

    @SuppressLint("InflateParams")
    override fun createView(): View {
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.mine_func_layout, null) as GridLayout
        addViewToGridLayout(rootView)

        val dataList = listOf(DataHolder(getContext()!!.resources.getString(R.string.mine_local_music), R.drawable.ic_my_music_local_song, R.id.local_music), DataHolder(getContext()!!.resources.getString(R.string.mine_down_music), R.drawable.ic_my_music_download_song, R.id.download_music), DataHolder(getContext()!!.resources.getString(R.string.mine_recent_music), R.drawable.ic_my_music_recent_playlist, R.id.recent_music), DataHolder(getContext()!!.resources.getString(R.string.mine_love_music), R.drawable.ic_my_music_my_favorite, R.id.love_music), DataHolder(getContext()!!.resources.getString(R.string.mine_buy_music), R.drawable.ic_my_music_paid_songs, R.id.buy_music), DataHolder(getContext()!!.resources.getString(R.string.mine_running_radio), R.drawable.ic_my_music_running_radio, R.id.running_radio))
        initView(dataList)
        return rootView
    }

    @Keep
    fun onDataChanged(cursor: Cursor) {
        val infoList = listOf(cursor.count.toString(), "2", "3", "4", "5")
        bindDataToView(infoList)
    }

    private fun addViewToGridLayout(container: ViewGroup) {
        for (i in 0..5) {
            val itemView = LayoutInflater.from(getContext()).inflate(R.layout.view_mine_item_view, container, false)
            val params: GridLayout.LayoutParams = itemView.layoutParams as GridLayout.LayoutParams
            params.width = BaseConfig.width / 3
            itemView.layoutParams = params
            container.addView(itemView)
        }
    }

    private fun initView(list: List<DataHolder>) {
        list.forEachIndexed { index, dataHolder ->
            val itemView = rootView.getChildAt(index)
            val imageView: ImageView = itemView.findViewById(R.id.img_item)
            val itemName: TextView = itemView.findViewById(R.id.tv_item_name)
            itemView.id = dataHolder.id
            itemView.setOnClickListener(this)
            itemName.text = dataHolder.itemName
            imageView.setImageDrawable(getActivity()!!.getDrawable(dataHolder.drawablePic))
        }
    }

    private fun bindDataToView(list: List<String>) {
        list.forEachIndexed { index, info ->
            val itemView = rootView.getChildAt(index)
            val itemCount: TextView = itemView.findViewById(R.id.tv_item_count)
            itemCount.text = info
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
        startFragment(getPage(), LocalMusicContainerFragment.newInstance(0), preFragmentTag)
    }

    private fun downloadMusic() {
        startFragment(getPage(), DownLoadFragment.newInstance(), preFragmentTag)
    }

    private fun recentMusic() {
        startFragment(getPage(), RecentMusicFragment.newInstance(), preFragmentTag)
    }

    private fun loveMusic() {
        startFragment(getPage(), LovedSongFragment.newInstance(), preFragmentTag)
    }

    private fun runningRadio() {
        startFragment(getPage(), LocalMusicContainerFragment.newInstance(0), preFragmentTag)
    }

    private fun buyMusic() {
        Toast.makeText(getContext(), "测试", Toast.LENGTH_SHORT).show()
    }

    class DataHolder(
            var itemName: String,
            var drawablePic: Int,
            var id: Int
    )
}