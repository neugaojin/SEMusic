package com.se.music.service

import android.os.Parcel
import android.os.RemoteException
import com.se.music.IMediaAidlInterface
import com.se.music.base.Null
import com.se.music.base.data.database.entity.MusicEntity
import java.io.File
import java.io.PrintWriter
import java.lang.ref.WeakReference
import java.util.*

/**
 *Author: gaojin
 *Time: 2018/9/16 下午7:50
 */

class ServiceStub constructor(service: MediaService) : IMediaAidlInterface.Stub() {

    private val mService: WeakReference<MediaService> = WeakReference(service)

    override fun isPlaying(): Boolean {
        return mService.get()?.isPlaying ?: false
    }

    override fun stop() {
        mService.get()?.stop()
    }

    override fun pause() {
        mService.get()?.pause()
    }

    override fun play() {
        mService.get()?.play()
    }

    override fun nextPlay() {
        mService.get()?.nextPlay()
    }

    override fun previous() {
        mService.get()?.previous()
    }

    override fun openFile(path: String) {
        mService.get()?.openFile(path)
    }

    @Suppress("UNCHECKED_CAST")
    override fun open(infos: Map<*, *>, list: LongArray, position: Int) {
        mService.get()?.open(infos as HashMap<Long, MusicEntity>, list, position)
    }

    override fun getArtistName(): String? {
        return mService.get()?.getArtistName()
    }

    override fun getTrackName(): String? {
        return mService.get()?.getTrackName()
    }

    override fun getAlbumName(): String? {
        return mService.get()?.getAlbumName()
    }

    override fun getAlbumPath(): String? {
        return mService.get()?.getAlbumPath()
    }

    override fun getAlbumPic(): String {
        return mService.get()?.getAlbumPic() ?: Null
    }

    override fun getAlbumId(): Long {
        return mService.get()?.getAlbumId() ?: 0
    }

    override fun getAlbumPathtAll(): Array<String?> {
        return mService.get()?.getAlbumPathAll() ?: arrayOfNulls(0)
    }

    override fun getPath(): String? {
        return null
    }

    override fun getPlaylistInfo(): Map<Long, MusicEntity> {
        return mService.get()?.getPlaylistInfo() ?: HashMap()
    }

    override fun getQueue(): LongArray {
        return mService.get()?.getQueue() ?: LongArray(0)
    }

    override fun getAudioId(): Long {
        return mService.get()?.getAudioId() ?: 0
    }

    override fun getQueueSize(): Int {
        return mService.get()?.getQueueSize() ?: 0
    }

    override fun getQueuePosition(): Int {
        return mService.get()?.getQueuePosition() ?: 0
    }

    override fun setQueuePosition(index: Int) {
        mService.get()?.setQueuePosition(index)
    }

    override fun removeTrack(id: Long): Int {
        return mService.get()?.removeTrack(id) ?: -1
    }

    override fun enqueue(list: LongArray, infos: Map<*, *>, action: Int) {
    }

    override fun getRepeatMode(): Int {
        return mService.get()?.getRepeatMode() ?: -1
    }

    override fun setRepeatMode(repeatmode: Int) {
        mService.get()?.setRepeatMode(repeatmode)
    }

    override fun duration(): Long {
        return mService.get()?.duration() ?: 0
    }

    override fun position(): Long {
        return mService.get()?.position() ?: 0
    }

    override fun seek(pos: Long): Long {
        return mService.get()?.seek(pos) ?: 0
    }

    override fun isTrackLocal(): Boolean {
        return mService.get()?.isTrackLocal() ?: true
    }

    override fun secondPosition(): Int {
        return mService.get()?.getSecondPosition() ?: 0
    }

    override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
        try {
            super.onTransact(code, data, reply, flags)
        } catch (e: RuntimeException) {
            e.printStackTrace()
            val file = File(mService.get()!!.cacheDir.absolutePath + "/err/")
            if (!file.exists()) {
                file.mkdirs()
            }
            try {
                val writer = PrintWriter(mService.get()!!.cacheDir.absolutePath + "/err/" + System.currentTimeMillis() + "_aidl.log")
                e.printStackTrace(writer)
                writer.close()
            } catch (e1: Exception) {
                e1.printStackTrace()
            }

            throw e
        } catch (e: RemoteException) {
            e.printStackTrace()
        }

        return true
    }
}