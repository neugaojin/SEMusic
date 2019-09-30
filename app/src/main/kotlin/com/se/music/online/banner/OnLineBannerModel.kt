package com.se.music.online.banner

import android.os.Bundle
import android.util.Log
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import com.se.music.base.BaseActivity
import com.se.music.online.model.HallModel
import com.se.music.retrofit.MusicRetrofit
import com.se.senet.callback.CallLoaderCallbacks
import retrofit2.Call

/**
 * Created by gaojin on 2018/2/4.
 */
class OnLineBannerModel(presenter: com.se.router.mvp.MvpPresenter, modelId: Int) : com.se.router.mvp.BaseModel<HallModel>(presenter, modelId) {

    override fun load() {
        LoaderManager.getInstance(getActivity() as BaseActivity).initLoader(getId(), null, buildHallCallBack())
    }

    /**
     * 请求轮播图片
     */
    private fun buildHallCallBack(): CallLoaderCallbacks<HallModel> {
        return object : CallLoaderCallbacks<HallModel>(getContext()!!) {
            override fun onCreateCall(id: Int, args: Bundle?): Call<HallModel> {
                return MusicRetrofit.INSTANCE.getMusicHall()
            }

            override fun onSuccess(loader: Loader<*>, data: HallModel) {
                dispatchData(data)
            }

            override fun onFailure(loader: Loader<*>, throwable: Throwable) {
                Log.e("MusicFragment", throwable.toString())
            }
        }
    }
}