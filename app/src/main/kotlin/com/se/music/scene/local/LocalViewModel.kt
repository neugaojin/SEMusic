package com.se.music.scene.local

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.se.music.database.DataBase
import com.se.music.entity.AlbumEntity
import com.se.music.entity.ArtistEntity
import com.se.music.entity.MusicEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *Author: gaojin
 *Time: 2019-12-08 16:35
 */

class LocalViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val DELAY_LOAD_TIME = 250L
    }

    val localSongInfo: MutableLiveData<List<MusicEntity>> by lazy {
        MutableLiveData<List<MusicEntity>>()
    }

    val localAlbumInfo: MutableLiveData<List<AlbumEntity>> by lazy {
        MutableLiveData<List<AlbumEntity>>()
    }

    val localSingerInfo: MutableLiveData<List<ArtistEntity>> by lazy {
        MutableLiveData<List<ArtistEntity>>()
    }

    fun loadMusic() {
        viewModelScope.launch {
            delay(DELAY_LOAD_TIME)
            localSongInfo.value = DataBase.queryLocalSong(getApplication())
        }
    }

    fun loadAlbum() {
        viewModelScope.launch {
            delay(DELAY_LOAD_TIME)
            localAlbumInfo.value = DataBase.queryLocalAlbum(getApplication())
        }
    }

    fun loadSinger() {
        viewModelScope.launch {
            delay(DELAY_LOAD_TIME)
            localSingerInfo.value = DataBase.queryLocalArtist(getApplication())
        }
    }
}