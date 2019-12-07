package com.se.music.init

import android.content.Intent
import android.os.Bundle
import android.os.RemoteException
import android.view.KeyEvent
import android.widget.Toast
import com.se.music.R
import com.se.music.base.BaseActivity
import com.se.music.base.TOAST_SHOW_DURATION
import com.se.music.fragment.MainFragment

class MainActivity : BaseActivity() {
    private var boo: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showQuickControl(true)

        // 防止Fragment重叠
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().run {
                add(R.id.se_main_content, MainFragment.newInstance(), MainFragment.TAG)
                commit()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager.findFragmentById(R.id.se_main_content)?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStart() {
        super.onStart()
        boo = 0
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return if (currentIsMain()) {
                if ((System.currentTimeMillis() - boo) > TOAST_SHOW_DURATION) {
                    Toast.makeText(this, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show()
                    boo = System.currentTimeMillis()
                } else {
                    confirmDoubleClickBack()
                }
                false
            } else {
                super.onKeyDown(keyCode, event)
            }
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

    private fun currentIsMain(): Boolean {
        val main = supportFragmentManager.findFragmentByTag(MainFragment.TAG)
        return main?.isVisible ?: false
    }
}
