package com.se.music.mvvm

/**
 *Author: gaojin
 *Time: 2020/10/10 4:29 PM
 */

sealed class SingerListUIModel {

    class SingerItemModel() : SingerListUIModel() {
        constructor(singer: Singer) : this()
    }

    class SingerListHeaderModel() : SingerListUIModel() {

    }
}