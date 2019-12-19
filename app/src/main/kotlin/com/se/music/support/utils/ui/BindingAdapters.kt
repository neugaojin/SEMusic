package com.se.music.support.utils.ui

import android.view.View
import androidx.databinding.BindingAdapter

/**
 *Author: gaojin
 *Time: 2019-07-05 17:58
 */
@BindingAdapter("visibleGone")
fun showHide(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.GONE
}