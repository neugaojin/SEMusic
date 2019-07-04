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
import com.se.music.adapter.AlbumListAdapter
import com.se.music.base.BaseFragment
import com.se.music.entity.AlbumEntity
import com.se.music.provider.metadata.albumSelection
import com.se.music.provider.metadata.info_album
import com.se.music.provider.metadata.localAlbumUri
import com.se.music.utils.QUERY_LOCAL_ALBUM
import com.se.music.utils.parseCursorToAlbumEntityList

/**
 *Author: gaojin
 *Time: 2018/7/8 下午5:21
 */

class LocalAlbumFragment : BaseFragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var albumListAdapter: AlbumListAdapter
    private val list = ArrayList<AlbumEntity>()

    companion object {
        fun newInstance(): LocalAlbumFragment {
            return LocalAlbumFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mRecyclerView = inflater.inflate(R.layout.fragment_local_recycler_view, container, false) as RecyclerView
        return mRecyclerView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        albumListAdapter = AlbumListAdapter(context!!, list, mLoaderManager)
        mRecyclerView.run {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = albumListAdapter
        }

        LoaderManager.getInstance(this).initLoader(QUERY_LOCAL_ALBUM, null, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(context!!, localAlbumUri, info_album, albumSelection.toString(), null, null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor) {
        parseCursorToAlbumEntityList(QUERY_LOCAL_ALBUM, data, list)
        albumListAdapter.notifyDataSetChanged()
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
    }
}