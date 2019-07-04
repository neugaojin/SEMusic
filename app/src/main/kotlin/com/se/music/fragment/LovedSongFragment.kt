package com.se.music.fragment

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.se.music.R
import com.se.music.adapter.MusicListAdapter
import com.se.music.base.BasePageFragment
import com.se.music.entity.MusicEntity
import com.se.music.provider.metadata.LOVE_SONG_CONTENT_URI
import com.se.music.utils.QUERY_LOVE_SONG
import com.se.music.utils.parseLoveSongCursorToMusicEntityList

/**
 *Author: gaojin
 *Time: 2018/10/25 下午10:24
 */

class LovedSongFragment : BasePageFragment(), LoaderManager.LoaderCallbacks<Cursor> {

    companion object {
        fun newInstance(): LovedSongFragment {
            return LovedSongFragment()
        }
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MusicListAdapter
    private val musicList = mutableListOf<MusicEntity>()

    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        recyclerView = inflater.inflate(R.layout.fragment_love_song, container, false) as RecyclerView
        return recyclerView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setTitle(context!!.getString(R.string.love_music_title))
        adapter = MusicListAdapter(context!!, musicList)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        mLoaderManager.initLoader(QUERY_LOVE_SONG, null, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(context!!, LOVE_SONG_CONTENT_URI, null, null, null, null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor) {
        parseLoveSongCursorToMusicEntityList(QUERY_LOVE_SONG, data, musicList)
        adapter.notifyDataSetChanged()
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
    }
}