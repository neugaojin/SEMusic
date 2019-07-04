package com.se.music.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

/**
 * Creator：gaojin
 * date：2017/11/1 下午10:56
 */
// 判断权限集合
fun lacksPermissions(context: Context, permissions: Array<String>): Boolean {
    return permissions.any { lacksPermission(context, it) }
}

// 判断是否缺少权限
private fun lacksPermission(context: Context, permission: String): Boolean {
    return ContextCompat.checkSelfPermission(context, permission) ==
            PackageManager.PERMISSION_DENIED
}
