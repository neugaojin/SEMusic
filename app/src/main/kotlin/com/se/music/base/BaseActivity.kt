package com.se.music.base

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.scene.NavigationSceneUtility
import com.bytedance.scene.Scene
import com.bytedance.scene.SceneDelegate

/**
 * Creator：gaojin
 * date：2017/11/6 下午7:57
 */
@SuppressLint("Registered")
abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var mDelegate: SceneDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT
        mDelegate = NavigationSceneUtility.setupWithActivity(this, savedInstanceState, getHomeSceneClass(), supportRestore())
    }

    abstract fun getHomeSceneClass(): Class<out Scene>

    abstract fun supportRestore(): Boolean

    override fun onBackPressed() {
        if (!this.mDelegate.onBackPressed()) {
            super.onBackPressed()
        }
    }
}