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
import com.se.music.base.BaseFragment
import com.se.music.entity.MusicEntity
import com.se.music.database.metadata.infoMusic
import com.se.music.database.metadata.localMusicUri
import com.se.music.database.metadata.songSelection
import com.se.music.singleton.SharePreferencesUtils
import com.se.music.utils.QUERY_LOCAL_SONG
import com.se.music.utils.parseCursorToMusicEntityList

/**
 * Created by gaojin on 2018/2/28.
 */
class LocalSongFragment : BaseFragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var adapter: MusicListAdapter
    private val musicList = mutableListOf<MusicEntity>()

    companion object {
        fun newInstance(): LocalSongFragment {
            return LocalSongFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRecyclerView = inflater.inflate(R.layout.fragment_local_recycler_view, container, false) as RecyclerView
        return mRecyclerView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = MusicListAdapter(context!!, musicList)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.adapter = adapter
        mLoaderManager.initLoader(QUERY_LOCAL_SONG, null, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(context!!, localMusicUri, infoMusic, songSelection.toString(), null, SharePreferencesUtils.getSongSortOrder())
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor) {
        parseCursorToMusicEntityList(QUERY_LOCAL_SONG, data, musicList)
        adapter.notifyDataSetChanged()
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
    }
}