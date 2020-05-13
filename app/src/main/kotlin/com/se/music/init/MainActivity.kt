package com.se.music.init

import android.os.RemoteException
import android.widget.Toast
import com.se.music.base.BaseActivity
import com.se.music.base.TOAST_SHOW_DURATION
import com.se.music.scene.RootScene


/**
 *Author: gaojin
 *Time: 2019-10-18 15:02
 */

class MainActivity : BaseActivity() {

    private var boo: Long = 0

    override fun getHomeSceneClass() = RootScene::class.java

    override fun supportRestore() = false

    override fun onStart() {
        super.onStart()
        boo = 0
    }

    override fun onBackPressed() {
        if (!mDelegate.onBackPressed()) {
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
            e.printStackTrace()
        }
    }
}