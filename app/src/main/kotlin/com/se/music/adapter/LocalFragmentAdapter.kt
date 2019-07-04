package com.se.music.adapter

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.se.music.R
import com.se.music.fragment.LocalAlbumFragment
import com.se.music.fragment.LocalFolderFragment
import com.se.music.fragment.LocalSingerFragment
import com.se.music.fragment.LocalSongFragment

/**
 * Created by gaojin on 2017/12/7.
 */
class LocalFragmentAdapter(fm: FragmentManager, context: Context) : FragmentStatePagerAdapter(fm) {
    private val mTabNames = mutableListOf<String>(context.getString(R.string.local_music_song, 0)
            , context.getString(R.string.local_music_singer, 0),
            context.getString(R.string.local_music_album, 0),
            context.getString(R.string.local_music_folder, 0))

    override fun getItem(position: Int) = when (position) {
        0 -> LocalSongFragment.newInstance()
        1 -> LocalSingerFragment.newInstance()
        2 -> LocalAlbumFragment.newInstance()
        else -> LocalFolderFragment.newInstance()
    }

    override fun getCount() = mTabNames.size

    override fun getPageTitle(position: Int) = mTabNames[position % 4]

    fun setTitle(position: Int, title: String) {
        if (position in 0 until mTabNames.size) {
            mTabNames[position] = title
            notifyDataSetChanged()
        }
    }
}