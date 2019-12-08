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
import com.se.music.adapter.SingerListAdapter
import com.se.music.base.BaseFragment
import com.se.music.entity.ArtistEntity
import com.se.music.database.metadata.info_artist
import com.se.music.database.metadata.localSingerUri
import com.se.music.database.metadata.artistSelection
import com.se.music.utils.QUERY_LOCAL_SINGER
import com.se.music.utils.parseCursorToArtistEntityList

/**
 *Author: gaojin
 *Time: 2018/5/31 下午11:32
 */

class LocalSingerFragment : BaseFragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var adapter: SingerListAdapter
    private val list = mutableListOf<ArtistEntity>()

    companion object {
        fun newInstance(): LocalSingerFragment {
            return LocalSingerFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mRecyclerView = inflater.inflate(R.layout.fragment_local_recycler_view, container, false) as RecyclerView
        return mRecyclerView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = SingerListAdapter(context!!, list)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.adapter = adapter
        mLoaderManager.initLoader(QUERY_LOCAL_SINGER, null, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(context!!, localSingerUri, info_artist, artistSelection, null, null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor) {
        parseCursorToArtistEntityList(data, list)
        adapter.notifyDataSetChanged()
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {}
}