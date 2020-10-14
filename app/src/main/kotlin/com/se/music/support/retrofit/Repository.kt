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
import com.se.music.online.params.CommonPostParams
import com.se.music.online.params.ExpressPostParams
import com.se.senet.base.GsonFactory
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit

/**
 * Created by gaojin on 2017/12/18.
 */
object Repository {

    private const val API_BASE_C_URL = "http://c.y.qq.com/"
    private const val API_BASE_U_URL = "http://u.y.qq.com/"
    private const val API_LAST_FM_URL = "http://ws.audioscrobbler.com/"
    private const val API_TING_BAIDU = "http://tingapi.ting.baidu.com/"
    private const val API_BASE_SO = "http://39.105.193.124"

    // QQ音乐
    private val baseCRetrofit: Retrofit = RetrofitFactory.create(API_BASE_C_URL, RetrofitFactory.Type.QQ)
    private val baseURetrofit: Retrofit = RetrofitFactory.create(API_BASE_U_URL, RetrofitFactory.Type.QQ)
    private val soRetrofit: Retrofit = RetrofitFactory.create(API_BASE_SO, RetrofitFactory.Type.SO)

    // lastFm
    private val baseLastFmRetrofit: Retrofit = RetrofitFactory.create(API_LAST_FM_URL, RetrofitFactory.Type.LAST_FM)

    // 百度音乐
    private val tingRetrofit: Retrofit = RetrofitFactory.create(API_TING_BAIDU, RetrofitFactory.Type.Ting)

    suspend fun getMusicHall(): Response<HallModel> {
        return baseCRetrofit.create(RetrofitService.QQ::class.java).getMusicHallService()
    }

    suspend fun getRecommendList(): Response<RecommendListModel> {
        val params = CommonPostParams()
        params.recomPlaylist.method = "get_hot_recommend"
        params.recomPlaylist.module = "playlist.HotRecommendServer"
        params.recomPlaylist.param.async = 1
        params.recomPlaylist.param.cmd = 2
        return baseURetrofit.create(RetrofitService.QQ::class.java)
                .getRecommendList(GsonFactory.INSTANCE.toJson(params))
    }

    suspend fun getSinger(pagesize: Int, pageNo: Int, area: Int, sex: Int, genre: Int): SingerEntity {
        return soRetrofit.create(RetrofitService.So::class.java).getSinger(pageNo, area, sex, genre)
    }

    suspend fun getSingerCategory() = soRetrofit.create(RetrofitService.So::class.java).getSingerCategory()

    suspend fun getNewSongInfo(): Response<ExpressInfoModel> {
        val expressPostParams = ExpressPostParams()
        expressPostParams.new_album.method = "GetNewSong"
        expressPostParams.new_album.module = "QQMusic.MusichallServer"
        expressPostParams.new_album.param.sort = 1
        expressPostParams.new_album.param.start = 0
        expressPostParams.new_album.param.end = 0

        expressPostParams.new_song.method = "GetNewAlbum"
        expressPostParams.new_song.module = "QQMusic.MusichallServer"
        expressPostParams.new_song.param.sort = 1
        expressPostParams.new_song.param.start = 0
        expressPostParams.new_song.param.end = 1

        return baseURetrofit.create(RetrofitService.QQ::class.java)
                .getNewSongInfo(GsonFactory.INSTANCE.toJson(expressPostParams))
    }

    fun getSingAvatar(artist: String): Call<Artist> {
        return baseLastFmRetrofit.create(RetrofitService.LastFm::class.java)
                .getSingerAvatar("artist.getinfo", artist)
    }

    fun getAlbumInfo(artist: String, album: String): Call<Album> {
        return baseLastFmRetrofit.create(RetrofitService.LastFm::class.java)
                .getAlbumInfo("album.getinfo", artist, album)
    }

    fun getRelatedSongInfo(song: String): Call<OtherVersionInfo> {
        return baseLastFmRetrofit.create(RetrofitService.LastFm::class.java)
                .getRelatedSongInfo("track.search", song, 3)
    }

    fun getSimilarSongInfo(song: String, artist: String): Call<SimilarSongInfo> {
        return baseLastFmRetrofit.create(RetrofitService.LastFm::class.java)
                .getSimilarSongInfo("track.getSimilar", song, artist, 3)
    }

    fun getLrcInfo(song: String): Call<LrcInfo> {
        return tingRetrofit.create(RetrofitService.Ting::class.java).getLrcInfo("baidu.ting.search.lrcys", song)
    }
}