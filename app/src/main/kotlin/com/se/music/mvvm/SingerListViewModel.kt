package com.se.music.mvvm

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.loader.content.Loader
import com.se.music.retrofit.MusicRetrofit
import com.se.senet.callback.CallLoaderCallbacks
import retrofit2.Call

/**
 *Author: gaojin
 *Time: 2019-07-04 19:55
 */

class SingerListViewModel(application: Application) : AndroidViewModel(application) {

    val mObservableSingers: MediatorLiveData<List<Singer>?> = MediatorLiveData()

    init {
        mObservableSingers.value = null
        //TODO 网络请求还得适配
        buildSingerCallback()
    }

    //相当于Model
    private fun buildSingerCallback(): CallLoaderCallbacks<SingerEntity> {
        return object : CallLoaderCallbacks<SingerEntity>(getApplication()) {
            override fun onCreateCall(id: Int, args: Bundle?): Call<SingerEntity> {
                return MusicRetrofit.INSTANCE.getSinger(100, 1)
            }

            override fun onSuccess(loader: Loader<*>, data: SingerEntity) {
                if (data.data?.list?.isEmpty() == false) {
                    mObservableSingers.value = data.data.list
                }
            }

            override fun onFailure(loader: Loader<*>, throwable: Throwable) {
                Log.e("OnLineSingerFragment", throwable.toString())
            }
        }
    }

    override fun onCleared() {
        //This method will be called when this ViewModel is no longer used and will be destroyed
        super.onCleared()
    }

}