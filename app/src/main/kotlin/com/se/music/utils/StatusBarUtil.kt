package com.se.music.utils

import android.app.Activity
import android.graphics.Color
import android.view.View

/**
 *Author: gaojin
 *Time: 2018/7/22 下午5:22
 */

/**
 * 设置状态栏透明
 * 5.0以上系统
 */
fun setTransparentForWindow(activity: Activity) {
    activity.window.statusBarColor = Color.TRANSPARENT
    activity.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
}