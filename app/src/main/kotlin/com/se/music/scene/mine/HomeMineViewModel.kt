package com.se.music.scene.mine

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.se.music.base.data.DataBase
import kotlinx.coroutines.launch

/**
 *Author: gaojin
 *Time: 2019-12-03 11:26
 */

class HomeMineViewModel(application: Application) : AndroidViewModel(application) {
    val musicCount: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    init {
        viewModelScope.launch {
            val count = DataBase.queryLocalSongCount()
            musicCount.value = count
        }
    }
}