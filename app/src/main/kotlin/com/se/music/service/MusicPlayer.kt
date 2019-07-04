package com.se.music.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import com.se.music.IMediaAidlInterface
import com.se.music.base.Null
import com.se.music.entity.MusicEntity
import java.util.*

/**
 * Created by gaojin on 2018/3/8.
 */
class MusicPlayer {

    companion object {
        var mService: IMediaAidlInterface? = null
        /**
         *context 和 ServiceConnection的映射，便于及时释放
         */
        private var mConnectionMap: WeakHashMap<Context, SeServiceConnection> = WeakHashMap()

        fun bindToService(context: Context): ServiceToken? {

            context.startService(Intent(context, MediaService::class.java))

            val serviceConnection = SeServiceConnection()

            val intent = Intent().setClass(context, MediaService::class.java)

            if (context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)) {
                mConnectionMap[context] = serviceConnection
                return ServiceToken(context)
            }
            return null
        }

        fun unbindFromService(token: ServiceToken) {
            val context = token.context
            val serviceConnection = mConnectionMap.remove(context) ?: return

            context.unbindService(serviceConnection)
            if (mConnectionMap.isEmpty()) {
                mService = null
            }
        }

        class SeServiceConnection : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                mService = IMediaAidlInterface.Stub.asInterface(service)
            }

            override fun onServiceDisconnected(name: ComponentName) {
                mService = null
            }
        }

        /**
         * 播放或者暂停音乐
         */
        fun playOrPause() {
            try {
                if (mService != null) {
                    if (mService!!.isPlaying) {
                        mService!!.pause()
                    } else {
                        mService!!.play()
                    }
                }
            } catch (ignored: Exception) {
                ignored.printStackTrace()
            }
        }

        fun isPlaying(): Boolean {
            return mService?.isPlaying ?: false
        }

        private fun getQueuePosition(): Int {
            return mService?.queuePosition ?: 0
        }

        /**
         * 播放所有音乐
         *
         * @param info ID到音乐实体的映射
         * @param list 音乐ID的集合
         * @param position 当前播放的音乐的位置
         */
        @Synchronized
        fun playAll(info: HashMap<Long, MusicEntity>, list: LongArray, position: Int) {
            if (list.isEmpty() || mService == null) {
                return
            }
            try {
                val currentId = mService!!.audioId
                val currentQueuePosition = getQueuePosition()
                if (position != -1) {
                    val playlist = getQueue()
                    if (Arrays.equals(list, playlist)) {
                        if (currentQueuePosition == position && currentId == list[position]) {
                            mService?.play()
                            return
                        } else {
                            mService?.queuePosition = position
                            return
                        }
                    }
                }
                if (position < 0) {
                    mService?.open(info, list, 0)
                } else {
                    mService?.open(info, list, position)
                }
                mService?.play()
            } catch (ignored: RemoteException) {
                ignored.printStackTrace()
            }
        }

        /**
         * 播放下一首音乐
         */
        fun nextPlay() {
            mService?.nextPlay()
        }

        /**
         * 上一首音乐
         */
        fun previous() {
            mService?.previous()
        }

        /**
         * 音乐已播放时间 (ms)
         */
        fun position(): Long {
            return mService?.position() ?: 0
        }

        /**
         * 音乐总时长 (ms)
         */
        fun duration(): Long {
            return mService?.duration() ?: 0
        }

        /**
         * 指定音乐播放位置
         */
        fun seek(position: Long) {
            mService?.seek(position)
        }

        fun getCurrentAlbumId(): Long {
            return mService?.albumId ?: -1
        }

        fun isTrackLocal(): Boolean {
            return mService?.isTrackLocal ?: false
        }

        fun secondPosition(): Int {
            return mService?.secondPosition() ?: 0
        }

        fun getAudioId(): Long {
            return mService?.audioId ?: 0
        }

        /**
         * 获取循环状态
         */
        fun getRepeatMode(): Int {
            return mService?.repeatMode ?: MediaService.REPEAT_ALL
        }

        fun setRepeatMode(repeatMode: Int) {
            mService?.repeatMode = repeatMode
        }

        /**
         * 获取当前播放的音乐的名字
         *
         * @return
         */
        fun getTrackName(): String {
            return mService?.trackName ?: Null
        }

        /**
         * 获取当前音乐歌手的名字
         *
         * @return
         */
        fun getArtistName(): String {
            return mService?.artistName ?: Null
        }

        fun getAlbumPic(): String {
            return mService?.albumPic ?: Null
        }

        fun getAlbumName(): String {
            return mService?.albumName ?: Null
        }

        fun getAlbumId(): Long {
            return mService?.albumId ?: 0
        }

        /**
         * 返回播放列表ID到音乐实体的映射
         *
         * @return
         */
        fun getPlayinfos(): HashMap<Long, MusicEntity>? {
            return mService?.playlistInfo as HashMap<Long, MusicEntity>
        }

        /**
         * 返回播放列表的ID
         *
         * @return
         */
        private fun getQueue(): LongArray? {
            return mService?.queue
        }

        fun setQueue(index: Int) {
            mService?.queuePosition = index
        }

        fun getCurrentAudioId(): Long {
            return mService?.audioId ?: 0
        }
    }
}