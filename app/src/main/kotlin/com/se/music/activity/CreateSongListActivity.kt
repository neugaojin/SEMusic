package com.se.music.activity

import android.content.ContentValues
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import com.se.music.R
import com.se.music.base.ToolBarActivity
import com.se.music.databinding.ActivityCreateSongListBinding
import com.se.music.provider.metadata.*
import com.se.music.utils.generateLoaderId
import java.util.*

/**
 *Author: gaojin
 *Time: 2018/5/13 下午5:49
 */

class CreateSongListActivity : ToolBarActivity() {

    companion object {
        val resultCode = generateLoaderId()
    }

    private lateinit var mBinding: ActivityCreateSongListBinding

    override fun createContentView(inflater: LayoutInflater, rootView: ViewGroup): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.activity_create_song_list, rootView, false)
        return mBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = resources.getString(R.string.create_new_song_list)
        mBinding.etListInfo.setText(R.string.create_new_song_list)
        mBinding.etListInfo.setSelectAllOnFocus(true)
        mBinding.etListInfo.requestFocus()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_save -> save()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun save() {
        contentResolver.insert(SONG_LIST_CONTENT_URI, ContentValues().apply {
            put(SL_ID, UUID.randomUUID().toString())
            put(SL_NAME, mBinding.etListName.text.toString())
            put(SL_CREATE_TIME, System.currentTimeMillis())
            put(SL_INFO, mBinding.etListInfo.text.toString())
        })
        setResult(resultCode)
        finish()
    }
}