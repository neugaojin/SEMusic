package com.se.music.activity

import android.os.Bundle
import android.view.*
import com.se.music.R
import com.se.music.base.ToolBarActivity
import com.se.music.support.utils.generateLoaderId

/**
 *Author: gaojin
 *Time: 2018/5/13 下午5:49
 */

class CreateSongListActivity : ToolBarActivity() {

    companion object {
        val resultCode = generateLoaderId()
    }


    override fun createContentView(inflater: LayoutInflater, rootView: ViewGroup): View {
        return inflater.inflate(R.layout.activity_create_song_list, rootView, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = resources.getString(R.string.create_new_song_list)
//        mBinding.etListInfo.setText(R.string.create_new_song_list)
//        mBinding.etListInfo.setSelectAllOnFocus(true)
//        mBinding.etListInfo.requestFocus()
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
//        contentResolver.insert(SONG_LIST_CONTENT_URI, ContentValues().apply {
//            put(SL_ID, UUID.randomUUID().toString())
//            put(SL_NAME, mBinding.etListName.text.toString())
//            put(SL_CREATE_TIME, System.currentTimeMillis())
//            put(SL_INFO, mBinding.etListInfo.text.toString())
//        })
//        setResult(resultCode)
//        finish()
    }
}