package com.se.music.init

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.se.music.R
import com.se.music.support.utils.lacksPermissions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 *Author: gaojin
 *Time: 2018/7/31 下午11:06
 */

class Welcome : AppCompatActivity() {

    companion object {
        const val PERMISSION_REQUEST_CODE = 0x00
        const val PACKAGE_URL_SCHEME = "package:"
        val permissions = Array(1) { Manifest.permission.READ_EXTERNAL_STORAGE }
    }

    private val presenterScope: CoroutineScope by lazy {
        CoroutineScope(Dispatchers.Main + Job())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (lacksPermissions(this, permissions)) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE)// 请求权限
        } else {
            jump()
        }
    }

    /**
     * 跳转到主页面
     */
    private fun jump() {
        presenterScope.launch {
            toMainActivity()
        }
    }

    private fun toMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.setPackage(packageName)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUEST_CODE && !grantResults.contains(PackageManager.PERMISSION_DENIED)) {
            //allPermissionsGranted
            jump()
        } else {
            AlertDialog.Builder(this).run {
                setTitle(R.string.help)
                setMessage(R.string.string_help_text)
                setNegativeButton(R.string.quit) { _, _ -> finish() }
                setPositiveButton(R.string.settings) { _, _ ->
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.parse(PACKAGE_URL_SCHEME + packageName)
                    startActivity(intent)
                }
                show()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        presenterScope.cancel()
    }
}