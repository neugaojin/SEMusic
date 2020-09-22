package com.se.music.support.retrofit

import com.se.music.entity.Album
import com.se.music.entity.Artist
import com.se.music.entity.LrcInfo
import com.se.music.entity.OtherVersionInfo
import com.se.music.entity.SimilarSongInfo
import com.se.music.mvvm.SingerEntity
import com.se.music.online.model.ExpressInfoModel
import com.se.music.online.model.HallModel
import com.se.music.online.model.RecommendListModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * Created by gaojin on 2017/12/18.
 */
interface RetrofitService {

    interface QQ {
        @GET("musichall/fcgi-bin/fcg_yqqhomepagerecommend.fcg")
        suspend fun getMusicHallService(): Response<HallModel>

        @GET("cgi-bin/musicu.fcg")
        suspend fun getRecommendList(@Query("data") data: String): Response<RecommendListModel>

        @GET("v8/fcg-bin/v8.fcg")
        suspend fun getSinger(@QueryMap map: Map<String, String>, @Query("pagesize") pagesize: Int, @Query("pagenum") pagenum: Int): Response<SingerEntity>

        @GET("cgi-bin/musicu.fcg")
        suspend fun getNewSongInfo(@Query("data") data: String): Response<ExpressInfoModel>
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