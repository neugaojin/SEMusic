package com.se.music.activity

import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.se.music.R
import com.se.music.base.BaseActivity
import com.se.music.scene.playing.PlayingRootScene

/**
 *Author: gaojin
 *Time: 2018/8/7 下午7:48
 */

class PlayingActivity : BaseActivity() {

    override fun getHomeSceneClass() = PlayingRootScene::class.java

    override fun supportRestore() = false

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_setting, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_setting -> Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show()
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.push_up_in, R.anim.push_down_out)
    }
}