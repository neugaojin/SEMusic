package com.se.music.mvvm

/**
 *Author: gaojin
 *Time: 2019-07-05 16:28
 */
data class SingerEntity(
        val data: SingerData?,
        val result: Int
)

data class SingerData(
        val area: Int,
        val genre: Int,
        val index: Int,
        val list: List<Singer>,
        val sex: Int,
        val total: Int
)

data class Singer(
        val country: String,
        val singer_id: Int,
        val singer_mid: String,
        val singer_name: String,
        val singer_pic: String
)

data class SingerCategory(
        val data: CategoryData?,
        val result: Int
)

data class CategoryData(
        val area: List<CategoryInfo>,
        val genre: List<CategoryInfo>,
        val index: List<CategoryInfo>,
        val sex: List<CategoryInfo>
)

data class CategoryInfo(
        val id: Int,
        val name: String
) {
    var index = 0
}