package com.se.music.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.se.music.R
import com.se.music.utils.lacksPermissions

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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        if (lacksPermissions(this, permissions)) {
            requestPermissions(permissions, PERMISSION_REQUEST_CODE) // 请求权限
        } else {
            allPermissionsGranted()
        }
    }

    private fun jump() {
        val intent = Intent(this, MainActivity::class.java)
        intent.setPackage(packageName)
        startActivity(intent)
        finish()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUEST_CODE && hasAllPermissionsGranted(grantResults)) {
            allPermissionsGranted()
        } else {
            showMissingPermissionDialog()
        }
    }

    // 显示缺失权限提示
    private fun showMissingPermissionDialog() {
        AlertDialog.Builder(this).run {
            setTitle(R.string.help)
            setMessage(R.string.string_help_text)
            setNegativeButton(R.string.quit) { _, _ -> finish() }
            setPositiveButton(R.string.settings) { _, _ -> startAppSettings() }
            show()
        }
    }

    // 含有全部的权限
    private fun hasAllPermissionsGranted(grantResults: IntArray): Boolean {
        return !grantResults.contains(PackageManager.PERMISSION_DENIED)
    }

    // 全部权限均已获取
    private fun allPermissionsGranted() {
        jump()
    }

    // 启动应用的设置
    private fun startAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse(PACKAGE_URL_SCHEME + packageName)
        startActivity(intent)
    }
}