package com.se.music.init

import android.content.Intent
import android.os.Bundle
import com.se.music.R
import com.se.music.base.BaseActivity

class MainActivity : BaseActivity() {
    private var boo: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showQuickControl(true)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager.findFragmentById(R.id.se_main_content)?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStart() {
        super.onStart()
        boo = 0
    }
}
