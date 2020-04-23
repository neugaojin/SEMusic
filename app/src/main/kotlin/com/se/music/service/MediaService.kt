package com.se.music.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.*
import android.os.*
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.se.music.R
import com.se.music.init.MainActivity
import com.se.music.base.APP_NAME
import com.se.music.base.Null
import com.se.music.base.data.database.entity.MusicEntity
import com.se.music.base.data.database.provider.ImageStore
import com.se.music.base.data.database.provider.RecentStore
import com.se.music.support.singleton.OkHttpSingleton
import com.se.senet.base.GsonFactory
import java.io.File
import java.io.RandomAccessFile
import java.lang.ref.WeakReference
import java.util.*

/**
 *Author: gaojin
 *Time: 2018/5/13 下午11:17
 * 后台播放服务
 */

class MediaService : Service() {

    companion object {
        const val TAG = "MediaService"
        const val TRACK_ENDED = 1
        /**
         * 自动切换下一首
         */
        const val TRACK_WENT_TO_NEXT = 2
        const val RELEASE_WAKELOCK = 3
        const val SERVER_DIED = 4
        const val FADE_DOWN = 6
        const val FADE_UP = 7
        const val LRC_DOWNLOADED = -10
        /**
         * 单曲循环
         */
        const val REPEAT_CURRENT = 1

        /**
         * 顺序播放
         */
        const val REPEAT_ALL = 2

        /**
         * 随机播放
         */
        const val REPEAT_SHUFFLER = 3

        const val MAX_HISTORY_SIZE = 1000

        const val LRC_PATH = "/semusic/lrc/"

        private const val TRACK_NAME = "trackname"
        private const val NOTIFY_MODE_NONE = 0
        private const val NOTIFY_MODE_FOREGROUND = 1
        private const val IDLE_DELAY = 5 * 60 * 1000
    }

    // public field
    var isPlaying = false
    var mRepeatMode = REPEAT_ALL
    var mLastSeekPos: Long = 0

    // handler
    private lateinit var mUrlHandler: Handler
    private lateinit var mLrcHandler: Handler
    private var mRequestLrc: RequestLrc? = null
    private lateinit var mPlayerHandler: MusicPlayerHandler
    private lateinit var mHandlerThread: HandlerThread
    private lateinit var mPlayer: MultiPlayer
    private lateinit var mNotificationManager: NotificationManager

    private val mShuffler = Shuffler.INSTANCE
    /**
     * 历史歌单 记录播放过的音乐的位置
     */
    private val mHistory = LinkedList<Int>()
    /**
     * 传进来的歌单
     */
    @SuppressLint("UseSparseArrays")
    private var mPlayListInfo = HashMap<Long, MusicEntity>()
    /**
     * 当前播放列表
     */
    private val mPlaylist = ArrayList<MusicTrack>(100)
    /**
     * 当前播放的音乐的实体
     */
    private var currentMusicEntity: MusicEntity? = null
    /**
     * 当前要播放音乐的地址
     */
    private var mFileToPlay: String = Null
    /**
     * 当前播放音乐的下标
     */
    private var mPlayPos = -1
    /**
     * 下一首歌的下标
     */
    private var mNextPlayPos = -1

    private var mOpenFailedCounter = 0
    private var mLastPlayedTime: Long = 0
    private var mPreferences: SharedPreferences? = null
    private var mRecentStore: RecentStore? = null
    private var mNotificationPostTime: Long = 0
    private var mNotifyMode = NOTIFY_MODE_NONE
    private var mServiceStartId = -1
    private val mNotificationId = 1000
    private val mCardId: Int = 0
    private var mServiceInUse = false

    /**
     * 接收广播通知栏的
     */
    private val intentReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            handleCommandIntent(intent)
        }
    }

    /**
     * 处理广播接受到的消息
     */
    private fun handleCommandIntent(intent: Intent) {
        when (intent.action) {
            TOGGLE_PAUSE_ACTION -> {
                if (isPlaying) {
                    pause()
                } else {
                    play()
                }
            }
            NEXT_ACTION -> nextPlay()
            REPEAT_ACTION -> {
                cycleRepeat()
            }
            SHUFFLE_ACTION -> {
            }
            TRY_GET_TRACK_INFO -> {
                getLrc(mPlaylist[mPlayPos].mId)
            }
            STOP_ACTION -> {
                pause()
                seek(0)
                releaseServiceUiAndStop()
            }
        }
    }

    private val mLrcThread = Thread(Runnable {
        Looper.prepare()
        mLrcHandler = Handler()
        Looper.loop()
    })

    private val mGetUrlThread = Thread(Runnable {
        Looper.prepare()
        mUrlHandler = Handler()
        Looper.loop()
    })

    // ============= Service 生命周期 Start===========//
    private val mBinder = ServiceStub(this)

    override fun onCreate() {
        super.onCreate()
        mGetUrlThread.start()
        mLrcThread.start()
        mHandlerThread = HandlerThread("MusicPlayerHandler", Process.THREAD_PRIORITY_BACKGROUND)
        mHandlerThread.start()
        mPlayerHandler = MusicPlayerHandler(this, mHandlerThread.looper)
        mPlayer = MultiPlayer(this, mPlayerHandler)
        mPreferences = getSharedPreferences("Service", 0)
        mRecentStore = RecentStore.instance

        // 接收广播
        val filter = IntentFilter().apply {
            addAction(TOGGLE_PAUSE_ACTION)
            addAction(STOP_ACTION)
            addAction(NEXT_ACTION)
            addAction(PREVIOUS_ACTION)
            addAction(PREVIOUS_FORCE_ACTION)
            addAction(REPEAT_ACTION)
            addAction(SHUFFLE_ACTION)
            addAction(TRY_GET_TRACK_INFO)
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(SEND_PROGRESS)
        }
        registerReceiver(intentReceiver, filter)

        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mServiceStartId = startId
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder {
        mServiceInUse = true
        return mBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        mServiceInUse = false
        if (isPlaying ||
                mPlaylist.size > 0 ||
                mPlayerHandler.hasMessages(TRACK_ENDED)) {
            return true
        }
        stopSelf(mServiceStartId)
        return true
    }

    override fun onRebind(intent: Intent?) {
        mServiceInUse = true
    }

    override fun onDestroy() {
        cancelNotification()
        mPlayerHandler.removeCallbacksAndMessages(null)
        mHandlerThread.quit()
        mPlayer.release()
        unregisterReceiver(intentReceiver)
    }
    // ============= Service 生命周期 End ===========//

    // ============= public方法 Start ===========//
    fun isTrackLocal(): Boolean {
        synchronized(this) {
            val info = mPlayListInfo[getAudioId()] ?: return true
            return info.islocal
        }
    }

    fun getTrackName(): String? {
        synchronized(this) {
            return currentMusicEntity?.musicName
        }
    }

    fun getAlbumPic(): String? {
        synchronized(this) {
            return ImageStore.instance.query(currentMusicEntity?.albumName.hashCode())
        }
    }

    fun getAlbumId(): Long {
        synchronized(this) {
            return 0
        }
    }

    fun getAlbumName(): String? {
        synchronized(this) {
            return currentMusicEntity?.albumName
        }
    }

    fun getArtistName(): String? {
        synchronized(this) {
            return currentMusicEntity?.artist
        }
    }

    fun getQueueSize(): Int {
        synchronized(this) {
            return mPlaylist.size
        }
    }

    fun getPlaylistInfo(): HashMap<Long, MusicEntity> {
        synchronized(this) {
            return mPlayListInfo
        }
    }

    fun getAlbumPathAll(): Array<String?> {
        synchronized(this) {
            try {
                val len = mPlayListInfo.size
                val albums = arrayOfNulls<String>(len)
                val queue = getQueue()
                for (i in 0 until len) {
                    albums[i] = mPlayListInfo[queue[i]]!!.albumData
                }
                return albums
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return arrayOf()
        }
    }

    fun getAudioId(): Long {
        val track = getCurrentTrack()
        return track?.mId ?: -1
    }

    private fun getCurrentTrack(): MusicTrack? {
        return getTrack(mPlayPos)
    }

    private fun getTrack(index: Int): MusicTrack? {
        synchronized(this) {
            return if (index >= 0 && index < mPlaylist.size) {
                mPlaylist[index]
            } else null
        }
    }

    fun getAlbumPath(): String? {
        synchronized(this) {
            return Null
        }
    }

    /**
     * 获得播放音乐的队列
     *
     * @return
     */
    fun getQueue(): LongArray {
        synchronized(this) {
            val len = mPlaylist.size
            val list = LongArray(len)
            for (i in 0 until len) {
                list[i] = mPlaylist[i].mId
            }
            return list
        }
    }

    /**
     * 获取播放队列的位置
     *
     * @return
     */
    fun getQueuePosition(): Int {
        synchronized(this) {
            return mPlayPos
        }
    }

    /**
     * 设置当前播放队列播放的歌曲
     *
     * @param index
     */
    fun setQueuePosition(index: Int) {
        synchronized(this) {
            mPlayPos = index
            openCurrent()
            play()
            notifyChange(META_CHANGED)
        }
    }

    /**
     * 设置当前播放的位置mPlayPos
     *
     * @param nextPos
     */
    fun setCurrentPlayPos(nextPos: Int) {
        synchronized(this) {
            mHistory.add(mPlayPos)
            if (mHistory.size > MAX_HISTORY_SIZE) {
                mHistory.remove()
            }
            mPlayPos = nextPos
        }
    }

    fun nextPlay() {
        synchronized(this) {
            var pos = mNextPlayPos
            if (pos < 0) {
                pos = getNextPosition()
            }
            if (pos < 0) {
                setIsPlaying(value = false, notify = false)
                return
            }
            stop(false)
            setCurrentPlayPos(pos)
            openCurrent()
            play()
            notifyChange(MUSIC_CHANGED)
        }
    }

    fun seek(position: Long): Long {
        var result: Long = -1
        if (mPlayer.isInitialized) {
            result = when {
                position < 0 -> mPlayer.seek(0)
                position > mPlayer.duration() -> mPlayer.seek(mPlayer.duration())
                else -> mPlayer.seek(position)
            }
            notifyChange(POSITION_CHANGED)
        }
        return result
    }

    fun position(): Long {
        if (mPlayer.isInitialized && mPlayer.isPlayerPrepared) {
            try {
                return mPlayer.position()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return -1
    }

    fun duration(): Long {
        return if (mPlayer.isInitialized && mPlayer.isPlayerPrepared) {
            mPlayer.duration()
        } else -1
    }

    fun getSecondPosition(): Int {
        return if (mPlayer.isInitialized) {
            mPlayer.secondaryPosition
        } else -1
    }

    fun getRepeatMode(): Int {
        return mRepeatMode
    }

    fun setRepeatMode(repeatMode: Int) {
        synchronized(this) {
            mRepeatMode = repeatMode
            setNextTrack()
            saveQueue(false)
            notifyChange(REPEAT_MODE_CHANGED)
        }
    }

    fun previous() {
        synchronized(this) {
            val goPrevious = getRepeatMode() != REPEAT_CURRENT
            if (goPrevious) {
                val pos = getPreviousPlayPosition(true)
                if (pos < 0) {
                    return
                }
                mNextPlayPos = mPlayPos
                mPlayPos = pos
                stop(false)
                openCurrent()
                play(false)
                notifyChange(META_CHANGED)
                notifyChange(MUSIC_CHANGED)
            } else {
                seek(0)
                play(false)
            }
        }
    }

    fun play() {
        // 默认设置下一首歌的位置
        play(true)
    }

    fun pause() {
        synchronized(this) {
            mPlayerHandler.removeMessages(FADE_UP)
            mPlayer.pause()
            setIsPlaying(value = false, notify = true)
            notifyChange(META_CHANGED)
        }
    }

    fun stop() {
        stop(true)
    }

    /**
     * 入口函数
     * @param info 音乐列表
     * @param list 音乐ID集合
     * @param position 要播放音乐的位置
     */
    fun open(info: HashMap<Long, MusicEntity>, list: LongArray, position: Int) {
        synchronized(this) {
            val oldId = getAudioId()
            mPlayListInfo = info

            // 判断是不是传入新的音乐列表
            val listLength = list.size
            var newList = true
            if (mPlaylist.size == listLength) {
                newList = false
                for (i in 0 until listLength) {
                    if (list[i] != mPlaylist[i].mId) {
                        newList = true
                        break
                    }
                }
            }
            if (newList) {
                addToPlayList(list, -1)
                notifyChange(QUEUE_CHANGED)
            }

            mPlayPos = if (position >= 0) {
                position
            } else {
                mShuffler.nextInt(mPlaylist.size)
            }
            mHistory.clear()
            openCurrent()

            if (oldId != getAudioId()) {
                notifyChange(META_CHANGED)
            }
        }
    }

    fun sendUpdateBuffer(progress: Int) {
        val intent = Intent(BUFFER_UP)
        intent.putExtra("progress", progress)
        sendBroadcast(intent)
    }

    fun loading(l: Boolean) {
        val intent = Intent(MUSIC_LOADING)
        intent.putExtra("isloading", l)
        sendBroadcast(intent)
    }

    fun removeTrack(id: Long): Int {
        var numberMoved = 0
        synchronized(this) {
            var i = 0
            while (i < mPlaylist.size) {
                if (mPlaylist[i].mId == id) {
                    numberMoved += removeTracksInternal(i, i)
                    i--
                }
                i++
            }
            mPlayListInfo.remove(id)
        }

        if (numberMoved > 0) {
            notifyChange(QUEUE_CHANGED)
        }
        return numberMoved
    }
    // ============= public方法 End ===========//

    private fun getPreviousPlayPosition(removeFromHistory: Boolean): Int {
        synchronized(this) {
            if (mRepeatMode == REPEAT_SHUFFLER) {
                val historySize = mHistory.size
                if (historySize == 0) {
                    return -1
                }
                val pos = mHistory[historySize - 1]
                if (removeFromHistory) {
                    mHistory.removeAt(historySize - 1)
                }
                return pos
            } else {
                return if (mPlayPos > 0) {
                    mPlayPos - 1
                } else {
                    mPlaylist.size - 1
                }
            }
        }
    }

    /**
     * 播放歌曲
     *
     * @param createNewNextTrack
     */
    private fun play(createNewNextTrack: Boolean) {
        if (createNewNextTrack) {
            setNextTrack()
        } else {
            // 上一首
            setNextTrack(mNextPlayPos)
        }
        mPlayer.start()
        mPlayerHandler.removeMessages(FADE_DOWN)
        mPlayerHandler.sendEmptyMessage(FADE_UP)
        setIsPlaying(value = true, notify = true)
        updateNotification()
        notifyChange(META_CHANGED)
    }

    /**
     * 设置isPlaying
     *
     * @param value
     * @param notify
     */
    private fun setIsPlaying(value: Boolean, notify: Boolean) {
        if (isPlaying != value) {
            isPlaying = value
            if (!isPlaying) {
                mLastPlayedTime = System.currentTimeMillis()
            }
            if (notify) {
                notifyChange(PLAY_STATE_CHANGED)
            }
        }
    }

    private fun stop(updateState: Boolean) {
        if (mPlayer.isInitialized) {
            mPlayer.stop()
        }
        mFileToPlay = Null
        if (updateState) {
            setIsPlaying(false, false)
        }
    }

    /**
     * 音乐加入播放列表
     *
     * @param list
     * @param position 传-1则清空列表
     */
    private fun addToPlayList(list: LongArray, position: Int) {
        var index = position
        val addLen = list.size
        if (index < 0) {
            mPlaylist.clear()
            index = 0
        }

        mPlaylist.ensureCapacity(mPlaylist.size + addLen)
        if (index > mPlaylist.size) {
            index = mPlaylist.size
        }

        val arrayList = ArrayList<MusicTrack>(addLen)
        for (i in list.indices) {
            arrayList.add(MusicTrack(list[i], i))
        }

        mPlaylist.addAll(index, arrayList)
        if (mPlaylist.size == 0) {
            notifyChange(META_CHANGED)
        }
    }

    /**
     * 准备当前歌曲
     */
    private fun openCurrent() {
        synchronized(this) {
            stop(false)
            if (mPlaylist.size == 0 ||
                    mPlayListInfo.size == 0 &&
                    mPlayPos >= mPlaylist.size) {
                clearPlayInfo()
                return
            }

            val id = getAudioId()
            getLrc(id)
            if (mPlayListInfo[id] == null) {
                return
            }

            // set current source
            if (!mPlayListInfo[id]!!.islocal) {
                // 在线歌曲
            } else {
                while (true) {
                    val entity = getMusicEntity()
                    if (entity != null &&
                            entity.audioId != 0L &&
                            openFile(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.toString() + "/" +
                                    getMusicEntity()?.audioId)) {
                        break
                    }
                    if (mOpenFailedCounter++ < 10 && mPlaylist.size > 1) {
                        val pos = getNextPosition()
                        if (pos < 0) {
                            break
                        }
                        stop(false)
                        mPlayPos = pos
                    } else {
                        mOpenFailedCounter = 0
                        break
                    }
                }
            }
        }
    }

    private fun getMusicEntity(): MusicEntity? {
        var entity: MusicEntity? = null
        if (mPlayPos in 0 until mPlaylist.size) {
            entity = mPlayListInfo[getAudioId()]
        }
        return entity
    }

    private fun clearPlayInfo() {
        val file = File(cacheDir.absolutePath + "playlist")
        if (file.exists()) {
            file.delete()
        }
    }

    fun notifyChange(what: String) {
        if (SEND_PROGRESS == what) {
            val intent = Intent(what)
            intent.putExtra("position", position())
            intent.putExtra("duration", duration())
            sendBroadcast(intent)
            return
        }
        if (what == POSITION_CHANGED) {
            return
        }
        val intent = Intent(what)
        intent.putExtra("id", getAudioId())
        intent.putExtra("artist", getArtistName())
        intent.putExtra("album", getAlbumName())
        intent.putExtra("track", getTrackName())
        intent.putExtra("playing", isPlaying)
        intent.putExtra("albumuri", getAlbumPath())
        intent.putExtra("islocal", isTrackLocal())
        sendBroadcast(intent)
        val musicIntent = Intent(intent)
        musicIntent.action = what.replace(TIMBER_PACKAGE_NAME, MUSIC_PACKAGE_NAME)
        sendBroadcast(musicIntent)
        if (what == META_CHANGED) {
            mRecentStore!!.addSongId(getAudioId())
            currentMusicEntity = getMusicEntity()
        } else if (what == QUEUE_CHANGED) {
            val intent1 = Intent("com.past.music.emptyplaylist")
            intent.putExtra("showorhide", "show")
            sendBroadcast(intent1)
            saveQueue(true)
            if (isPlaying) {
                if (mNextPlayPos >= 0 && mNextPlayPos < mPlaylist.size) {
                    setNextTrack(mNextPlayPos)
                } else {
                    setNextTrack()
                }
            }
        } else {
            saveQueue(false)
        }
        if (what == PLAY_STATE_CHANGED) {
            updateNotification()
        }
    }

    private fun saveQueue(full: Boolean) {
        val editor = mPreferences!!.edit()
        if (full) {
            if (mPlayListInfo.size > 0) {
                val temp = GsonFactory.INSTANCE.toJson(mPlayListInfo)
                try {
                    val file = File(cacheDir.absolutePath + "playlist")
                    val ra = RandomAccessFile(file, "rws")
                    ra.write(temp.toByteArray())
                    ra.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            editor.putInt("cardid", mCardId)
        }
        editor.putInt("curpos", mPlayPos)
        if (mPlayer.isInitialized) {
            editor.putLong("seekpos", mPlayer.position())
        }
        editor.putInt("repeatmode", mRepeatMode)
        editor.apply()
    }

    /**
     * 获取下一首音乐的位置
     * @return
     */
    private fun getNextPosition(): Int {
        if (mPlaylist.isEmpty()) {
            return -1
        }
        return when (mRepeatMode) {
            REPEAT_CURRENT -> if (mPlayPos < 0) {
                0
            } else mPlayPos
            REPEAT_SHUFFLER -> {
                shuffleMode()
            }
            REPEAT_ALL -> if (mPlayPos >= mPlaylist.size - 1) {
                if (mRepeatMode == REPEAT_ALL) 0 else -1
            } else {
                mPlayPos + 1
            }
            else -> -1
        }
    }

    /**
     * 随机播放
     */
    private fun shuffleMode(): Int {
        // 随机播放未完成
        val songNumber = mPlaylist.size

        // 每个位置的数字代表播放过的次数
        // 初始值为0 代表此位置的未播放过
        val songNumPlays = IntArray(songNumber)

        mHistory.forEach {
            if (it in 0 until songNumber) {
                songNumPlays[it] += 1
            }
        }

        if (mPlayPos in 0 until songNumber) {
            songNumPlays[mPlayPos] += 1
        }

        // 播放过最少的次数 一般是未播放
        var minNumPlays = Integer.MAX_VALUE

        // 播放过最少的次数的音乐的数量
        var numSongsWithMinNumPlays = 0

        songNumPlays.forEach {
            if (it < minNumPlays) {
                minNumPlays = it
                numSongsWithMinNumPlays = 1
            } else if (it == minNumPlays) {
                numSongsWithMinNumPlays++
            }
        }

        var skip = mShuffler.nextInt(numSongsWithMinNumPlays)
        songNumPlays.forEachIndexed { index, value ->
            if (value == minNumPlays) {
                // 是最少播放次数的
                if (skip == 0) {
                    return index
                } else {
                    skip--
                }
            }
        }
        return -1
    }

    private fun setNextTrack() {
        setNextTrack(getNextPosition())
    }

    /**
     * 设置下一首将要播放的音乐的信息
     *
     * @param position
     */
    private fun setNextTrack(position: Int) {
        mNextPlayPos = position

        if (mNextPlayPos >= 0 && mNextPlayPos < mPlaylist.size) {
            val id = mPlaylist[mNextPlayPos].mId
            if (mPlayListInfo[id] != null) {
                if (mPlayListInfo[id]!!.islocal) {
                    mPlayer.setNextDataSource(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.toString() + "/" + id)
                } else {
                    mPlayer.setNextDataSource(null)
                }
            }
        } else {
            mPlayer.setNextDataSource(null)
        }
    }

    fun openFile(path: String): Boolean {
        synchronized(this) {
            mFileToPlay = path
            mPlayer.setDataSource(mFileToPlay)
            if (mPlayer.isInitialized) {
                mOpenFailedCounter = 0
                return true
            }
            var trackName = getTrackName()
            if (TextUtils.isEmpty(trackName)) {
                trackName = path
            }
            sendErrorMessage(trackName!!)
            stop(true)
            return false
        }
    }

    private fun sendErrorMessage(trackName: String) {
        val intent = Intent(TRACK_ERROR)
        intent.putExtra(TRACK_NAME, trackName)
        sendBroadcast(intent)
    }

    private fun getLrc(id: Long) {
        val info = mPlayListInfo[id]
        val lrc = Environment.getExternalStorageDirectory().absolutePath + LRC_PATH
        var file = File(lrc)
        if (!file.exists()) {
            // 不存在就建立此目录
            file.mkdirs()
        }
        file = File("$lrc$id.lrc")
        if (!file.exists()) {
            // 获取歌词
//            MusicRetrofit.INSTANCE
//                    .getLrcInfo(info!!.musicName)
//                    .enqueue(object : Callback<LrcInfo> {
//                        override fun onResponse(call: Call<LrcInfo>, response: Response<LrcInfo>) {
//                            val responseLrc = response.body()
//                            val lrcUrl: String
//                            if (responseLrc != null) {
//                                lrcUrl = responseLrc.lrcys_list?.get(0)?.lrclink ?: Null
//                                if (mRequestLrc != null) {
//                                    mLrcHandler.removeCallbacks(mRequestLrc)
//                                }
//                                mRequestLrc = RequestLrc(lrcUrl, getMusicEntity())
//                                mLrcHandler.postDelayed(mRequestLrc, 70)
//                            }
//                        }
//
//                        override fun onFailure(call: Call<LrcInfo>, t: Throwable) {
//                        }
//                    })
        } else {
            mPlayerHandler.sendEmptyMessage(LRC_DOWNLOADED)
        }
    }

    inner class RequestLrc(val url: String, private val musicEntity: MusicEntity?) : Runnable {
        override fun run() {
            if (url.isNotEmpty() && musicEntity != null) {
                val file = File(Environment.getExternalStorageDirectory().absolutePath + LRC_PATH + musicEntity.audioId + ".lrc")
                val storageFile = File(Environment.getExternalStorageDirectory().absolutePath + LRC_PATH)
                val lrcInfo = OkHttpSingleton.getResponseString(url)
                if (lrcInfo.isNotEmpty()) {
                    // 部分机型创建目录成功之后才能创建文件
                    if (!storageFile.exists()) {
                        storageFile.mkdirs()
                    }
                    if (!file.exists()) {
                        file.createNewFile()
                    }
                    file.writeText(lrcInfo)
                    mPlayerHandler.sendEmptyMessage(LRC_DOWNLOADED)
                }
            }
        }
    }

    private fun recentlyPlayed(): Boolean {
        return isPlaying || System.currentTimeMillis() - mLastPlayedTime < IDLE_DELAY
    }

    private fun releaseServiceUiAndStop() {
        if (isPlaying || mPlayerHandler.hasMessages(TRACK_ENDED)) {
            return
        }
        cancelNotification()
        if (!mServiceInUse) {
            stopSelf(mServiceStartId)
        }
    }

    private fun updateNotification() {
        val notifyMode: Int = if (isPlaying) {
            NOTIFY_MODE_FOREGROUND
        } else {
            NOTIFY_MODE_NONE
        }
        if (notifyMode == mNotifyMode) {
            mNotificationManager.notify(mNotificationId, getNotification())
        } else {
            if (notifyMode == NOTIFY_MODE_FOREGROUND) {
//                startForeground(mNotificationId, getNotification())
            } else {
                mNotificationManager.notify(mNotificationId, getNotification())
            }
        }
        mNotifyMode = notifyMode
    }

    private fun removeTracksInternal(first: Int, last: Int): Int {
        var inFirst = first
        var inLast = last
        synchronized(this) {
            when {
                inLast < inFirst -> return 0
                inFirst < 0 -> inFirst = 0
                inLast >= mPlaylist.size -> inLast = mPlaylist.size - 1
            }

            var goToNext = false
            if (mPlayPos in inFirst..inLast) {
                mPlayPos = inFirst
                goToNext = true
            } else if (mPlayPos > inLast) {
                mPlayPos -= inLast - inFirst + 1
            }
            val numToRemove = inLast - inFirst + 1

            if (inFirst == 0 && inLast == mPlaylist.size - 1) {
                mPlayPos = -1
                mNextPlayPos = -1
                mPlaylist.clear()
                mHistory.clear()
            } else {
                for (i in 0 until numToRemove) {
                    mPlayListInfo.remove(mPlaylist[inFirst].mId)
                    mPlaylist.removeAt(inFirst)
                }
                val positionIterator = mHistory.listIterator()
                while (positionIterator.hasNext()) {
                    val pos = positionIterator.next()
                    if (pos in inFirst..inLast) {
                        positionIterator.remove()
                    } else if (pos > inLast) {
                        positionIterator.set(pos - numToRemove)
                    }
                }
            }
            if (goToNext) {
                if (mPlaylist.size == 0) {
                    stop(true)
                    mPlayPos = -1
                } else {
                    if (mPlayPos >= mPlaylist.size) {
                        mPlayPos = 0
                    }
                    val wasPlaying = isPlaying
                    stop(false)
                    openCurrent()
                    if (wasPlaying) {
                        play()
                    }
                }
                notifyChange(META_CHANGED)
            }
            return inLast - inFirst + 1
        }
    }

    private fun cancelNotification() {
        stopForeground(true)
        mNotificationManager.cancel(hashCode())
        mNotificationManager.cancel(mNotificationId)
        mNotificationPostTime = 0
        mNotifyMode = NOTIFY_MODE_NONE
    }

    private fun cycleRepeat() {
        if (mRepeatMode == REPEAT_ALL) {
            setRepeatMode(REPEAT_CURRENT)
        } else {
            setRepeatMode(REPEAT_ALL)
        }
    }

    private fun getNotification(): Notification {
        val remoteViews = RemoteViews(this.packageName, R.layout.remote_view)
        val pauseFlag = 0x1
        val nextFlag = 0x2
        val stopFlag = 0x3
        val albumName = getAlbumName()
        val artistName = getArtistName()
        val isPlaying = isPlaying

        val text = if (TextUtils.isEmpty(albumName)) artistName else "$artistName - $albumName"
        remoteViews.setTextViewText(R.id.title, getTrackName())
        remoteViews.setTextViewText(R.id.text, text)

        // 此处action不能是一样的 如果一样的 接受的flag参数只是第一个设置的值
        val pauseIntent = Intent(TOGGLE_PAUSE_ACTION)
        pauseIntent.putExtra("FLAG", pauseFlag)
        val pausePIntent = PendingIntent.getBroadcast(this, 0, pauseIntent, 0)
        remoteViews.setImageViewResource(R.id.img_play, if (isPlaying) R.drawable.icon_remote_pause else R.drawable.icon_remote_play)
        remoteViews.setOnClickPendingIntent(R.id.img_play, pausePIntent)

        val nextIntent = Intent(NEXT_ACTION)
        nextIntent.putExtra("FLAG", nextFlag)
        val nextPIntent = PendingIntent.getBroadcast(this, 0, nextIntent, 0)
        remoteViews.setOnClickPendingIntent(R.id.img_next_play, nextPIntent)

        val preIntent = Intent(STOP_ACTION)
        preIntent.putExtra("FLAG", stopFlag)
        val prePIntent = PendingIntent.getBroadcast(this, 0, preIntent, 0)
        remoteViews.setOnClickPendingIntent(R.id.img_cancel, prePIntent)

        val mMainIntent = Intent(this, MainActivity::class.java)
        val mainIntent = PendingIntent.getActivity(this, 0, mMainIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (mNotificationPostTime == 0L) {
            mNotificationPostTime = System.currentTimeMillis()
        }
        var mNotification: Notification? = null
        if (mNotification == null) {
            val builder = NotificationCompat.Builder(this, APP_NAME)
                    .setContent(remoteViews)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentIntent(mainIntent)
                    .setWhen(mNotificationPostTime)
            mNotification = builder.build()
        } else {
            mNotification.contentView = remoteViews
        }
        return mNotification!!
    }

    private class MusicPlayerHandler constructor(service: MediaService, looper: Looper) : Handler(looper) {
        private val mService: WeakReference<MediaService> = WeakReference(service)
        override fun handleMessage(msg: Message) {
            val service = mService.get() ?: return
            service.run {
                when (msg.what) {
                    TRACK_WENT_TO_NEXT -> {
                        setCurrentPlayPos(service.mNextPlayPos)
                        setNextTrack()
                        notifyChange(META_CHANGED)
                        notifyChange(MUSIC_CHANGED)
                        notifyChange(LRC_UPDATED)
                        updateNotification()
                    }
                    TRACK_ENDED -> if (mRepeatMode == REPEAT_CURRENT) {
                        seek(0)
                        play()
                    } else {
                        nextPlay()
                    }
                    LRC_DOWNLOADED -> notifyChange(LRC_UPDATED)
                }
            }
        }
    }
}