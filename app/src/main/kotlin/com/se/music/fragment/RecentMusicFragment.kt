package com.se.music.fragment

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.se.music.R
import com.se.music.base.BasePageFragment
import com.se.music.database.database.provider.RecentStore
import com.se.music.database.database.recent.Song
import com.se.music.utils.inflate

/**
 * Created by gaojin on 2017/12/14.
 */
class RecentMusicFragment : BasePageFragment() {
    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        val content = LayoutInflater.from(context).inflate(R.layout.activity_recent_music, container, false)
        mRecyclerView = content.findViewById(R.id.recent_music_recycle)
        return content
    }

    internal var mRecyclerView: RecyclerView? = null

    private var mAdapter: Adapter? = null
    private var mList: List<Song> = ArrayList()
    private var recentStore: RecentStore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recentStore = RecentStore.instance
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container:
            ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        LoadSongs().execute("")
        return super.onCreateView(inflater, container, savedInstanceState)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setTitle(context!!.getString(R.string.recent_music_title))
        mRecyclerView!!.layoutManager = LinearLayoutManager(context)
        mRecyclerView!!.setHasFixedSize(true)
    }

    // 异步加载RecyclerView界面
    @SuppressLint("CI_StaticFieldLeak")
    private inner class LoadSongs : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String): String {
            mAdapter = Adapter(mList)
            return "Executed"
        }

        override fun onPostExecute(result: String) {
            mRecyclerView!!.adapter = mAdapter
        }

        override fun onPreExecute() {}
    }

    inner class Adapter(private var mList: List<Song>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        init {
            if (mList == null) {
                throw IllegalArgumentException("model Data must not be null")
            }
        }

        // 更新adpter的数据
        fun updateDataSet(list: List<Song>) {
            this.mList = list
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return if (viewType == HEAD_LAYOUT) {
                CommonItemViewHolder(parent.inflate(R.layout.common_item))
            } else {
                ListItemViewHolder(parent.inflate(R.layout.fragment_music_song_item))
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is ListItemViewHolder) {
                holder.onBindData(mList!![position - 1])
            }
        }

        override fun getItemCount(): Int {
            return if (null != mList) mList!!.size + 1 else 0
        }

        override fun getItemViewType(position: Int): Int {
            return if (position == 0) {
                HEAD_LAYOUT
            } else {
                CONTENT_LAYOUT
            }
        }

        internal inner class CommonItemViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

            private var select: ImageView? = null

            init {
                select = view.findViewById(R.id.select)
                view.setOnClickListener(this)
            }

            override fun onClick(v: View) {
            }
        }

        internal inner class ListItemViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

            private var mMusicName: TextView? = null
            private var mMusicInfo: TextView? = null
            private var mViewPagerButton: ImageView? = null

            private fun setListButton() {
            }

            init {
                mMusicName = itemView.findViewById(R.id.music_name)
                mMusicInfo = itemView.findViewById(R.id.music_info)
                mViewPagerButton = itemView.findViewById(R.id.viewpager_list_button)
                mViewPagerButton!!.setOnClickListener { setListButton() }
                view.setOnClickListener(this)
            }

            override fun onClick(v: View) {
            }

            fun onBindData(song: Song) {
                mMusicName!!.text = song.title
                mMusicInfo!!.text = song.artistName
            }
        }
    }

    companion object {
        val HEAD_LAYOUT = 0X01
        val CONTENT_LAYOUT = 0X02

        fun newInstance(): RecentMusicFragment {
            val fragment = RecentMusicFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}