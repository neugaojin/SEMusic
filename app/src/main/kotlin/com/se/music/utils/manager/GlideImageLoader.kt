package com.se.music.utils.manager

import android.content.Context
import android.widget.ImageView
import com.se.music.GlideApp
import com.youth.banner.loader.ImageLoader

/**
 * Created by gaojin on 2017/12/24.
 */
class GlideImageLoader : ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView) {
        GlideApp.with(context!!).load(path).into(imageView)
    }
}