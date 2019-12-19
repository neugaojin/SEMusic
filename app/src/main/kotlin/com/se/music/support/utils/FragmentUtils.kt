package com.se.music.support.utils

import androidx.fragment.app.Fragment
import com.se.music.R
import com.se.music.base.BaseActivity

/**
 *Author: gaojin
 *Time: 2018/5/27 下午11:52
 * Fragment跳转工具
 */

fun startFragment(current: Fragment, target: Fragment, tag: String) {
    current.fragmentManager!!.beginTransaction().run {
        setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out)
        (current.context as BaseActivity).supportFragmentManager.findFragmentByTag(tag)?.let { hide(it) }
        addToBackStack(null)
        add(R.id.se_main_content, target)
        commit()
    }
}

fun startFragment(current: com.se.router.mvp.MvpPage, target: Fragment, tag: String) {
    if (current is Fragment) {
        startFragment(current as Fragment, target, tag)
    }
}