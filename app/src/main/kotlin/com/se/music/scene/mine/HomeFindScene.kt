package com.se.music.scene.mine

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bytedance.scene.group.UserVisibleHintGroupScene
import com.se.music.R
import com.se.music.base.log.Loger

/**
 *Author: gaojin
 *Time: 2019-10-21 16:32
 */
class HomeFindScene : UserVisibleHintGroupScene() {

    private lateinit var imageView: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): ViewGroup {
        return inflater.inflate(R.layout.fragment_local_folder, container, false) as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.text_view).setOnClickListener {
        }
        imageView = requireViewById(R.id.image_view)
        createBitmap()
    }

    private fun createBitmap() {
        var bitmap = BitmapFactory.decodeResource(resources, R.drawable.avatar)
        imageView.setImageBitmap(bitmap)
        Loger.e { bitmap.isRecycled }
        bitmap = null
    }
}