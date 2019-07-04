package com.se.music.mine.listtitle

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.Keep
import androidx.core.content.ContextCompat
import androidx.loader.content.Loader
import com.se.music.R
import com.se.music.activity.CreateSongListActivity
import com.se.music.base.mvp.BaseView
import com.se.music.base.mvp.MvpPresenter
import com.se.music.mine.event.CollectEvent
import com.se.music.mine.event.CreateEvent
import com.se.music.retrofit.callback.CallLoaderCallbacks
import retrofit2.Call

/**
 * Author: gaojin
 * Time: 2018/5/7 下午9:22
 */
class MineSongListTitleView(
    presenter: MvpPresenter,
    viewId: Int,
    view: View
) : BaseView(presenter, viewId), View.OnClickListener {

    private lateinit var titleCreated: TextView
    private lateinit var titleCollected: TextView
    private lateinit var songListCount: TextView
    private lateinit var addSongList: View
    private lateinit var manageSongList: View
    private lateinit var llCreateNewSongList: View
    private lateinit var tvNoCollectedSongList: View

    private var createSongListSize = 0
    private var collectedSongListSize = 0
    private var isCreateTab = true

    init {
        initView(view)
        val value = object : CallLoaderCallbacks<String>(getContext()!!) {
            override fun onCreateCall(id: Int, args: Bundle?): Call<String> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onSuccess(loader: Loader<*>, data: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onFailure(loader: Loader<*>, throwable: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
    }

    @SuppressLint("InflateParams")
    override fun createView(): View {
        val rootView = LayoutInflater.from(getContext()).inflate(R.layout.mine_song_list_title_layout, null)
        titleCreated = rootView.findViewById(R.id.tv_title_created)
        titleCollected = rootView.findViewById(R.id.tv_title_collected)
        songListCount = rootView.findViewById(R.id.song_list_count)
        addSongList = rootView.findViewById(R.id.add_song_list)
        manageSongList = rootView.findViewById(R.id.manage_song_list)
        llCreateNewSongList = rootView.findViewById(R.id.ll_create_new_song_list)
        tvNoCollectedSongList = rootView.findViewById(R.id.no_collected_song_list)

        titleCreated.setOnClickListener(this)
        titleCollected.setOnClickListener(this)
        addSongList.setOnClickListener(this)
        manageSongList.setOnClickListener(this)
        llCreateNewSongList.setOnClickListener(this)

        bindView()
        return rootView
    }

    private fun bindView() {
        if (isCreateTab) {
            titleCreated.setTextColor(ContextCompat.getColor(getContext()!!, R.color.light_black))
            titleCollected.setTextColor(ContextCompat.getColor(getContext()!!, R.color.gray))
        }
    }

    private fun clickCreated() {
        if (isCreateTab) {
            return
        }
        titleCreated.setTextColor(ContextCompat.getColor(getContext()!!, R.color.light_black))
        titleCollected.setTextColor(ContextCompat.getColor(getContext()!!, R.color.gray))
        songListCount.text = getContext()!!.resources.getString(R.string.song_list_count, createSongListSize)
        addSongList.visibility = View.VISIBLE
        tvNoCollectedSongList.visibility = View.GONE
        llCreateNewSongList.visibility = if (createSongListSize == 0) View.VISIBLE else View.GONE
        dispatchData(CreateEvent())
        isCreateTab = true
    }

    private fun clickCollected() {
        if (!isCreateTab) {
            return
        }
        titleCollected.setTextColor(ContextCompat.getColor(getContext()!!, R.color.light_black))
        titleCreated.setTextColor(ContextCompat.getColor(getContext()!!, R.color.gray))
        songListCount.text = getContext()!!.resources.getString(R.string.song_list_count, collectedSongListSize)
        addSongList.visibility = View.GONE
        llCreateNewSongList.visibility = View.GONE
        tvNoCollectedSongList.visibility = if (collectedSongListSize == 0) View.VISIBLE else View.GONE
        dispatchData(CollectEvent())
        isCreateTab = false
    }

    private fun createNewSongList() {
        val intent = Intent(getContext(), CreateSongListActivity::class.java)
        getActivity()!!.startActivityForResult(intent, 0)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_title_created -> clickCreated()
            R.id.tv_title_collected -> clickCollected()
            R.id.ll_create_new_song_list -> createNewSongList()
            R.id.add_song_list -> createNewSongList()
        }
    }

    @Keep
    fun onDataChanged(cursor: Cursor) {
        createSongListSize = cursor.count
        collectedSongListSize = cursor.count

        if (isCreateTab) {
            songListCount.text = getContext()!!.resources.getString(R.string.song_list_count, createSongListSize)
            tvNoCollectedSongList.visibility = View.GONE
            llCreateNewSongList.visibility = if (createSongListSize == 0) View.VISIBLE else View.GONE
        } else {
            songListCount.text = getContext()!!.resources.getString(R.string.song_list_count, collectedSongListSize)
            llCreateNewSongList.visibility = View.GONE
            tvNoCollectedSongList.visibility = if (collectedSongListSize == 0) View.VISIBLE else View.GONE
        }
    }
}