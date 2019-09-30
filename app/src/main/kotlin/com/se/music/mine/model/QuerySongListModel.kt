package com.se.music.mine.model

import android.database.Cursor
import android.os.Bundle
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.se.music.base.BaseActivity
import com.se.music.provider.metadata.SONG_LIST_CONTENT_URI

/**
 *Author: gaojin
 *Time: 2018/5/21 下午10:22
 * 查询歌单Model
 */

class QuerySongListModel(presenter: com.se.router.mvp.MvpPresenter, private val modelId: Int) : com.se.router.mvp.BaseModel<Cursor>(presenter, modelId), LoaderManager.LoaderCallbacks<Cursor> {

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(getContext()!!, SONG_LIST_CONTENT_URI, null, null, null, null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        if (data != null) {
            dispatchData(data)
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
    }

    override fun load() {
        LoaderManager.getInstance(getActivity() as BaseActivity).initLoader(modelId, null, this)
    }
}