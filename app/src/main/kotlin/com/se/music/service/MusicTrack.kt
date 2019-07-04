package com.se.music.service

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by gaojin on 2018/3/8.
 */

class MusicTrack() : Parcelable {
    /**
     * 音乐的ID
     */
    var mId: Long = 0
    /**
     * 此首歌在播放列表中的位置
     */
    var mSourcePosition: Int = 0

    constructor(mId: Long, mSourcePosition: Int) : this() {
        this.mId = mId
        this.mSourcePosition = mSourcePosition
    }

    constructor(parcel: Parcel) : this() {
        mId = parcel.readLong()
        mSourcePosition = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(mId)
        parcel.writeInt(mSourcePosition)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MusicTrack> {
        override fun createFromParcel(parcel: Parcel): MusicTrack {
            return MusicTrack(parcel)
        }

        override fun newArray(size: Int): Array<MusicTrack?> {
            return arrayOfNulls(size)
        }
    }
}