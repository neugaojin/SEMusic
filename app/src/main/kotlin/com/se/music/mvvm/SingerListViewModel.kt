package com.se.music.mvvm

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.loader.content.Loader
import com.se.music.retrofit.MusicRetrofit
import com.se.music.retrofit.Repository
import com.se.senet.callback.CallLoaderCallbacks
import kotlinx.coroutines.*
import retrofit2.Call

/**
 *Author: gaojin
 *Time: 2019-07-04 19:55
 */

class SingerListViewModel(application: Application) : AndroidViewModel(application) {

    val mObservableSingers: MediatorLiveData<List<Singer>?> = MediatorLiveData()

    private val presenterScope: CoroutineScope by lazy {
        CoroutineScope(Dispatchers.Main + Job())
    }

    init {
        presenterScope.launch {
            val result = Repository.getSinger()
            if (result.data?.list?.isEmpty() == false) {
                mObservableSingers.value = result.data.list
            }
        }
    }

    override fun onCleared() {
        presenterScope.cancel()
    }

}