package com.se.music.mine

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.se.music.R
import com.se.music.activity.CreateSongListActivity
import com.se.music.entity.SongListEntity
import com.se.music.mine.event.CollectEvent
import com.se.music.mine.event.CreateEvent
import com.se.music.mine.listtitle.MineSongListTitleView
import com.se.music.mine.model.QueryLocalSongModel
import com.se.music.mine.model.QuerySongListModel
import com.se.music.mine.operation.MineOperationView
import com.se.music.mine.personal.MinePersonalInfoView
import com.se.music.mine.root.MineAdapter
import com.se.music.support.database.metadata.START_FROM_LOCAL
import com.se.music.support.utils.QUERY_LOCAL_SONG
import com.se.music.support.utils.QUERY_SONG_LIST
import com.se.music.support.utils.parseCursorToSongList
import com.se.router.mvp.BasePresenter
import com.se.router.mvp.MvpPage
import com.se.router.mvp.MvpPresenter

/**
 * Author: gaojin
 * Time: 2018/4/22 下午9:35
 * 首页【我的】Tab页面
 */
class MvpMineFragment : Fragment(), MvpPage {
    private val presenter: MvpPresenter = BasePresenter(this)
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MineAdapter
    private val list = mutableListOf<SongListEntity>()

    companion object {
        fun newInstance(): MvpMineFragment {
            return MvpMineFragment()
        }
    }

    override fun onPageError(exception: Exception) {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_mine_mvp, container, false)
        recyclerView = rootView.findViewById(R.id.mine_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = MineAdapter(context!!, list)
        recyclerView.adapter = adapter
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.add(MinePersonalInfoView(presenter, R.id.mine_personal_info, adapter.header))
        presenter.add(MineOperationView(presenter, R.id.mine_fun_area, adapter.header))
        presenter.add(MineSongListTitleView(presenter, R.id.mine_song_list_title, adapter.header))

        presenter.add(QuerySongListModel(presenter, QUERY_SONG_LIST))
        presenter.add(QueryLocalSongModel(presenter, QUERY_LOCAL_SONG, START_FROM_LOCAL))

        presenter.start(QUERY_SONG_LIST, QUERY_LOCAL_SONG)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == CreateSongListActivity.resultCode) {
//            presenter.reload(IdUtils.QUERY_SONG_LIST)
        }
    }

    @Keep
    fun onViewChanged(id: Int, collectEvent: CollectEvent) {
        adapter.notifyDataSetChanged()
    }

    @Keep
    fun onViewChanged(id: Int, createEvent: CreateEvent) {
        adapter.notifyDataSetChanged()
    }

    @Keep
    fun onModelChanged(id: Int, cursor: Cursor) {
        if (id == QUERY_SONG_LIST) {
            list.addAll(parseCursorToSongList(id, cursor))
            if (!list.isEmpty()) {
                adapter.notifyItemRangeChanged(1, list.size)
            }
            presenter.dispatchModelDataToView(id, cursor, R.id.mine_song_list_title)
        } else if (id == QUERY_LOCAL_SONG) {
            presenter.dispatchModelDataToView(id, cursor, R.id.mine_fun_area)
        }
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}