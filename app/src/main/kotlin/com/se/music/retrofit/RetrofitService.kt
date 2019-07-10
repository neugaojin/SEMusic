package com.se.music.retrofit

import com.se.music.entity.*
import com.se.music.mvvm.SingerEntity
import com.se.music.online.model.ExpressInfoModel
import com.se.music.online.model.HallModel
import com.se.music.online.model.RecommendListModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * Created by gaojin on 2017/12/18.
 */
interface RetrofitService {

    interface QQ {
        @GET("musichall/fcgi-bin/fcg_yqqhomepagerecommend.fcg")
        fun getMusicHallService(): Call<HallModel>

        @GET("cgi-bin/musicu.fcg")
        fun getRecommendList(@Query("data") data: String): Call<RecommendListModel>

        @GET("v8/fcg-bin/v8.fcg")
        fun getSinger(@QueryMap map: Map<String, String>, @Query("pagesize") pagesize: Int, @Query("pagenum") pagenum: Int): Call<SingerEntity>

        @GET("cgi-bin/musicu.fcg")
        fun getNewSongInfo(@Query("data") data: String): Call<ExpressInfoModel>
    }

    interface LastFm {
        @GET("2.0/")
        fun getSingerAvatar(@Query("method") method: String, @Query("artist") artist: String): Call<Artist>

        @GET("2.0/")
        fun getAlbumInfo(
                @Query("method") method: String,
                @Query("artist") artist: String,
                @Query("album") album: String
        ): Call<Album>

        @GET("2.0/")
        fun getRelatedSongInfo(
                @Query("method") method: String,
                @Query("track") track: String,
                @Query("limit") limit: Int
        ): Call<OtherVersionInfo>

        @GET("2.0/")
        fun getSimilarSongInfo(
                @Query("method") method: String,
                @Query("track") track: String,
                @Query("artist") artist: String,
                @Query("limit") limit: Int
        ): Call<SimilarSongInfo>
    }

    interface Ting {
        @GET("v1/restserver/ting")
        fun getLrcInfo(
                @Query("method") method: String,
                @Query("query") query: String
        ): Call<LrcInfo>
    }
}