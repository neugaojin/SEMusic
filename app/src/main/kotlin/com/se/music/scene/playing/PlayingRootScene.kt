package com.se.music.scene.playing

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bytedance.scene.group.GroupScene
import com.bytedance.scene.group.ScenePlaceHolderView
import com.se.music.R

/**
 *Author: gaojin
 *Time: 2020/5/13 5:52 PM
 */

class PlayingRootScene : GroupScene() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): ViewGroup {
        return inflater.inflate(R.layout.activity_playing, container, false) as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireViewById<ScenePlaceHolderView>(R.id.playing_first_area).apply {
            sceneName = PlayingMainScene::class.java.name
            sceneTag = PlayingMainScene.TAG
        }
        requireViewById<ScenePlaceHolderView>(R.id.playing_second_area).apply {
            sceneName = PlayingBottomScene::class.java.name
            sceneTag = PlayingBottomScene.TAG
        }
    }

    private fun setBackground() {
//        if (MusicPlayer.getAlbumPic().isEmpty()) {
//            activityBg.background = getDrawable(R.drawable.player_background_real)
//        } else {
//            Glide.with(this)
//                    .asBitmap()
//                    .load(MusicPlayer.getAlbumPic().getMegaImageUrl())
//                    .into(object : SimpleTarget<Bitmap>() {
//                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                            val blurBitmap = blurBitmap(resource, 100)
//                            if (blurBitmap != null) {
//                                activityBg.background = getAlphaDrawable(blurBitmap)
//                            }
//                        }
//
//                        override fun onLoadFailed(errorDrawable: Drawable?) {
//                            super.onLoadFailed(errorDrawable)
//                            activityBg.background = getDrawable(R.drawable.player_background_real)
//                        }
//                    })
//        }
    }

    private fun getAlphaDrawable(bg: Bitmap): Drawable {
        val newBitmap = Bitmap.createBitmap(bg.copy(Bitmap.Config.ARGB_8888, true))
        val canvas = Canvas(newBitmap)
        val paint = Paint()
        paint.color = ContextCompat.getColor(sceneContext!!, R.color.hex_33000000)
        canvas.drawRect(0f, 0f, bg.width.toFloat(), bg.height.toFloat(), paint)
        canvas.save()
        canvas.restore()
        return BitmapDrawable(resources, newBitmap)
    }
}