package com.se.music.online.recommend

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.se.music.R
import com.se.music.online.model.RecommendListModel
import com.se.music.widget.GridItemDecoration
import com.se.music.widget.RecommendItemView

/**
 * Created by gaojin on 2017/12/31.
 */
class RecommendSongListBlock : LinearLayout {

    private lateinit var recommendRecycleView: RecyclerView
    private lateinit var iconEnter: View

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    fun init() {
        orientation = LinearLayout.VERTICAL
        setBackgroundColor(resources.getColor(R.color.white))
        View.inflate(context, R.layout.online_day_recommend_song_block, this)
        initView()
    }

    private fun initView() {
        iconEnter = findViewById(R.id.recommend_icon_enter)
        recommendRecycleView = findViewById(R.id.recommend_recycle_view)
        recommendRecycleView.layoutManager = GridLayoutManager(context, 3)

        val mDividerItemDecoration = GridItemDecoration(context, LinearLayoutManager.HORIZONTAL, 3)
        mDividerItemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.transparent_divider)!!)
        recommendRecycleView.addItemDecoration(mDividerItemDecoration)
        recommendRecycleView.isNestedScrollingEnabled = false
    }

    fun dataChanged(data: RecommendListModel) {
        recommendRecycleView.adapter = RecommendGridAdapter(context, data.recomPlaylist?.data?.v_hot)
    }

    class RecommendGridAdapter(context: Context, v_hot: List<RecommendListModel.VHotBean>?) : RecyclerView.Adapter<RecommendViewHolder>() {
        private var context: Context = context
        private val ITEMCOUNT = 6
        private var hotList = v_hot
        override fun onBindViewHolder(holderRecommend: RecommendViewHolder, position: Int) {
            val vHotBean = hotList?.get(position)
            holderRecommend.recommendView.setImageView(vHotBean?.cover!!).setDescription(vHotBean.title!!)
        }

        override fun getItemCount(): Int {
            return when {
                hotList?.isEmpty() ?: true -> 0
                hotList!!.size < 6 -> hotList!!.size
                else -> ITEMCOUNT
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
            return RecommendViewHolder(RecommendItemView(context))
        }
    }

    class RecommendViewHolder(itemView: RecommendItemView) : RecyclerView.ViewHolder(itemView) {
        var recommendView: RecommendItemView = itemView
    }
}