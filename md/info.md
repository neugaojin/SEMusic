Kotlin实现

# 数据库表结构
- SongListTable
- 歌单表
- [SongListContentProvider]

| _id    | _name  | _count     | _creator | _create_time | _pic       | _info    |
| ------ | ------ | ---------- | -------- | ------------ | ---------- | -------- |
| id主键 | 歌单名 | 歌曲的数量 | 创建者   | 创建时间     | 歌单封面图 | 歌单信息 |

# 接口信息

注：接口全部抓取自 Web版的[QQ音乐](https://y.qq.com/)  (仅学习交流使用)

| 名称     | URL                                      | Query |
| ------ | ---------------------------------------- | ----- |
| 轮播图+电台 | https://c.y.qq.com/musichall/fcgi-bin/fcg_yqqhomepagerecommend.fcg |       |
| 排行榜    | https://c.y.qq.com/v8/fcg-bin/fcg_myqq_toplist.fcg |       |
| 搜索热词   | https://c.y.qq.com/splcloud/fcgi-bin/gethotkey.fcg |       |
| 歌手列表   | https://c.y.qq.com/v8/fcg-bin/v8.fcg?channel=singer&page=list&key=all_all_all&pagesize=100&pagenum=1&format=jsonp |       |

https://u.y.qq.com/cgi-bin/musicu.fcg?data={"comm":{"ct":24},"recomPlaylist":{"method":"get_hot_recommend","param":{"async":1,"cmd":2},"module":"playlist.HotRecommendServer"}}

"category":{"method":"get_hot_category","param":{"qq":""},"module":"music.web_category_svr"},"

"new_song":{"module":"QQMusic.MusichallServer","method":"GetNewSong","param":{"type":0}},"

"playlist":{"method":"get_playlist_by_category","param":{"id":8,"curPage":1,"size":40,"order":5,"titleid":8},"module":"playlist.PlayListPlazaServer"},"

"new_album":{"module":"QQMusic.MusichallServer","method":"GetNewAlbum","param":{"type":0,"category":"-1","genre":0,"year":1,"company":-1,"sort":1,"start":0,"end":39}},"

"toplist":{"module":"music.web_toplist_svr","method":"get_toplist_index","param":{}},"

"recomPlaylist":{"method":"get_hot_recommend","param":{"async":1,"cmd":2},"module":"playlist.HotRecommendServer"},"

"focus":{"module":"QQMusic.MusichallServer","method":"GetFocus","param":{}}

[SongListContentProvider]: https://github.com/neugaojin/SEMusic/blob/master/app/src/main/java/com/se/music/provider/SongListContentProvider.kt
<!-- @IGNORE PREVIOUS: link -->
