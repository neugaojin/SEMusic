package com.se.music.scene.mine

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.se.music.R
import com.se.music.online.model.HallModel
import com.se.music.retrofit.MusicRetrofit
import com.se.music.scene.fixed.UserVisibleHintGroupScene
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *Author: gaojin
 *Time: 2019-10-21 16:32
 */

class HomeFindScene : UserVisibleHintGroupScene() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): ViewGroup {
        return inflater.inflate(R.layout.fragment_local_folder, container, false) as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.text_view).setOnClickListener {
            test()
        }
    }

    private fun test() {
        val apiList = mutableListOf<Observable<HallModel>>()
        apiList.add(MusicRetrofit.INSTANCE.getMusicHallV1().subscribeOn(Schedulers.io()))
        apiList.add(MusicRetrofit.INSTANCE.getMusicHallV2().subscribeOn(Schedulers.io()))
        apiList.add(MusicRetrofit.INSTANCE.getMusicHallV1().subscribeOn(Schedulers.io()))
        apiList.add(MusicRetrofit.INSTANCE.getMusicHallV1().subscribeOn(Schedulers.io()))

        val hallModelList = mutableListOf<HallModel>()
        Log.e("gaojin", "start------------------>")
        Observable.zip(apiList) { response ->
            Log.e("gaojin", Thread.currentThread().name)
            response.forEach {
                Log.e("gaojin_test", "$it")
                if (it is HallModel) {
                    hallModelList.add(it)
                }
            }
            return@zip hallModelList
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e("gaojin", Thread.currentThread().name)
                    Log.e("gaojin", "${it.size}")
                }, {
                    Log.e("gaojin", Thread.currentThread().name)
                    Log.e("gaojin", "error了")
                }, {
                    Log.e("gaojin", Thread.currentThread().name)
                    Log.e("gaojin", "onComplete")
                })
    }


}