package com.se.music.initiate

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.se.music.base.BaseConfig
import com.se.music.base.log.Loger

/**
 *Author: gaojin
 *Time: 2020/9/22 8:25 PM
 */

class BaseUtilInitializer : Initializer<Unit> {
    companion object {
        const val APP_NAME = "SeMusic"
    }

    override fun create(context: Context) {
        Log.e("gj", "BaseUtilInitializer create")
        BaseConfig.init(context)
        Loger.init(APP_NAME)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}