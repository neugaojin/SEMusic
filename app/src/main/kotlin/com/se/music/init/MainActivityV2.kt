package com.se.music.init

import android.graphics.Color
import android.os.Bundle
import android.os.RemoteException
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.bytedance.scene.NavigationSceneUtility
import com.bytedance.scene.SceneDelegate
import com.se.music.base.BaseActivity
import com.se.music.base.TOAST_SHOW_DURATION
import com.se.music.scene.RootScene


/**
 *Author: gaojin
 *Time: 2019-10-18 15:02
 */

class MainActivityV2 : BaseActivity() {

    private var boo: Long = 0
    private lateinit var mDelegate: SceneDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT
        mDelegate = NavigationSceneUtility.setupWithActivity(this, savedInstanceState, RootScene::class.java, false)
    }

    override fun onStart() {
        super.onStart()
        boo = 0
    }

    override fun onBackPressed() {
        if (!this.mDelegate.onBackPressed()) {
            if ((System.currentTimeMillis() - boo) > TOAST_SHOW_DURATION) {
                Toast.makeText(this, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show()
                boo = System.currentTimeMillis()
            } else {
                confirmDoubleClickBack()
            }
        }
    }

    private fun confirmDoubleClickBack() {
        if (isDestroyed) {
            return
        }
        try {
            moveTaskToBack(true)
        } catch (e: RemoteException) {
            //Empty
        }
    }
}