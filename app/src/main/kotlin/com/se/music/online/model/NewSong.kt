package com.se.music.online.model

/**
 * Created by gaojin on 2018/1/28.
 */
class NewSong {
    var data: DataBean? = null
    var code: Int = 0

    class DataBean {
        var size: Int = 0
        var type: Int = 0
        var album_list: List<AlbumListBean>? = null
        var category_info: List<CategoryInfoBean>? = null
        var company_info: List<CompanyInfoBean>? = null
        var genre_info: List<GenreInfoBean>? = null
        var type_info: List<TypeInfoBean>? = null
        var year_info: List<YearInfoBean>? = null

        class AlbumListBean {
            var album: AlbumBean? = null
            var author: List<AuthorBean>? = null

            class AlbumBean {
                var id: Int = 0
                var mid: String? = null
                var name: String? = null
                var subtitle: String? = null
                var time_public: String? = null
                var title: String? = null
            }

            class AuthorBean {

                var id: Int = 0
                var mid: String? = null
                var name: String? = null
                var title: String? = null
                var type: Int = 0
                var uin: Int = 0
            }
        }

        class CategoryInfoBean {

            var id: Int = 0
            var title: String? = null
        }

        class CompanyInfoBean {

            var id: Int = 0
            var title: String? = null
        }

        class GenreInfoBean {

            var id: Int = 0
            var title: String? = null
        }

        class TypeInfoBean {

            var id: Int = 0
            var report: String? = null
            var title: String? = null
        }

        class YearInfoBean {

            var id: Int = 0
            var title: String? = null
        }
    }
}