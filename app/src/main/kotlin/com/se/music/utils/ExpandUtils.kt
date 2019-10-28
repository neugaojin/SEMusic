package com.se.music.utils

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bytedance.scene.Scene
import com.se.music.GlideApp

/**
 *Author: gaojin
 *Time: 2018/9/2 下午5:20
 */

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun ImageView.loadUrl(url: String?) {
    GlideApp.with(context)
            .load(url)
            .into(this)
}

fun ImageView.loadUrl(url: String?, @DrawableRes drawableRes: Int) {
    GlideApp.with(context)
            .load(url)
            .placeholder(drawableRes)
            .into(this)
}

/**
 * 设置状态栏透明
 * 5.0以上系统
 */
fun setTransparentForWindow(activity: Activity) {
    activity.window.statusBarColor = Color.TRANSPARENT
    activity.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
}