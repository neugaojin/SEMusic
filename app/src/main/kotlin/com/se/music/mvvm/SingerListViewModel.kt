package com.se.music.mvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import com.se.music.support.retrofit.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

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
            val result = Repository.getSinger(100, 1).body()
            if (result?.data?.list?.isEmpty() == false) {
                mObservableSingers.value = result.data.list
            }
        }
    }

    override fun onCleared() {
        presenterScope.cancel()
    }

}