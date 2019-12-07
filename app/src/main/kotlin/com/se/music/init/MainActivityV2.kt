package com.se.music.init

import android.os.Bundle
import android.os.RemoteException
import android.view.KeyEvent
import android.widget.Toast
import com.bytedance.scene.NavigationSceneUtility
import com.se.music.base.BaseActivity
import com.se.music.base.TOAST_SHOW_DURATION
import com.se.music.scene.RootScene
import com.se.music.utils.setTransparentForWindow


/**
 *Author: gaojin
 *Time: 2019-10-18 15:02
 */

class MainActivityV2 : BaseActivity() {

    private var boo: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTransparentForWindow(this)
        NavigationSceneUtility.setupWithActivity(this, savedInstanceState, RootScene::class.java, false)
    }

    override fun onStart() {
        super.onStart()
        boo = 0
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - boo) > TOAST_SHOW_DURATION) {
                Toast.makeText(this, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show()
                boo = System.currentTimeMillis()
            } else {
                confirmDoubleClickBack()
            }
            return false
        }
        return super.onKeyDown(keyCode, event)
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