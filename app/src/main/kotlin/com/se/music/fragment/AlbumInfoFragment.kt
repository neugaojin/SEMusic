package com.se.music.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.TextView
import com.se.music.R
import com.se.music.base.BaseFragment
import com.se.music.service.MusicPlayer
import com.se.music.support.utils.getMegaImageUrl
import com.se.music.support.utils.loadUrl
import com.se.music.widget.CircleImageView

/**
 *Author: gaojin
 *Time: 2018/10/7 下午7:49
 */

class AlbumInfoFragment : BaseFragment() {
    companion object {
        const val TAG = "AlbumInfoFragment"
        const val ANIMATION_DURATION = 30000L // 30s
        fun newInstance(): AlbumInfoFragment {
            return AlbumInfoFragment()
        }
    }

    private lateinit var albumView: CircleImageView
    private lateinit var artistName: TextView
    private lateinit var circleAnim: ObjectAnimator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_playing_album_info, container, false)
        view.tag = TAG
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        albumView = view.findViewById(R.id.album_pic)
        artistName = view.findViewById(R.id.artist_name)

        circleAnim = ObjectAnimator.ofFloat(albumView, "rotation", 0f, 360f).apply {
            interpolator = LinearInterpolator()
            repeatCount = -1
            duration = ANIMATION_DURATION
            start()
        }

        artistName.text = MusicPlayer.getArtistName()
        if (MusicPlayer.getAlbumPic().isEmpty()) {
            albumView.setImageResource(R.drawable.player_albumcover_default)
        } else {
            albumView.loadUrl(MusicPlayer.getAlbumPic().getMegaImageUrl(), R.drawable.player_albumcover_default)
        }
    }

    override fun onResume() {
        super.onResume()
        setAnimation()
    }

    override fun musciChanged() {
        circleAnim.start()
        setAnimation()
        artistName.text = MusicPlayer.getArtistName()
        if (MusicPlayer.getAlbumPic().isEmpty()) {
            albumView.setImageResource(R.drawable.player_albumcover_default)
        } else {
            albumView.loadUrl(MusicPlayer.getAlbumPic().getMegaImageUrl(), R.drawable.player_albumcover_default)
        }
    }

    private fun setAnimation() {
        if (MusicPlayer.isPlaying()) {
            if (circleAnim.isPaused) {
                circleAnim.resume()
            }
        } else {
            if (circleAnim.isRunning) {
                circleAnim.pause()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        circleAnim.end()
    }

    override fun updatePlayInfo() {
        setAnimation()
    }
}