package com.se.music.fragment

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.se.music.R
import com.se.music.base.BaseFragment
import com.se.music.service.MediaService
import com.se.music.service.MusicPlayer
import com.se.music.utils.manager.GlobalPlayTimeManager
import com.se.music.utils.manager.PlayTimeListener
import com.se.music.widget.lrc.LrcView
import java.io.File

/**
 *Author: gaojin
 *Time: 2018/10/7 下午7:49
 */

class LrcInfoFragment : BaseFragment(), PlayTimeListener {

    private lateinit var lrcView: LrcView

    companion object {
        const val TAG = "LrcInfoFragment"
        fun newInstance(): LrcInfoFragment {
            return LrcInfoFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalPlayTimeManager.registerListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_playing_lrc_info, container, false)
        lrcView = view.findViewById(R.id.playing_lrc_view)
        view.tag = TAG
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lrcView.updateTime(0)
    }

    override fun onDestroy() {
        super.onDestroy()
        GlobalPlayTimeManager.unregisterListener(this)
    }

    override fun updateLrc() {
        val file = File(Environment.getExternalStorageDirectory().absolutePath + MediaService.LRC_PATH + MusicPlayer.getAudioId() + ".lrc")
        if (file.exists()) {
            lrcView.loadLrc(file)
        }
    }

    override fun playedTime(position: Long, duration: Long) {
        lrcView.updateTime(position)
    }
}