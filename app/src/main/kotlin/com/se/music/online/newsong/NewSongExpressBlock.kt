package com.se.music.online.newsong

import android.content.Context
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.se.music.R
import com.se.music.base.picBaseUrl_150
import com.se.music.online.model.ExpressInfoModel
import com.se.music.widget.GridItemDecoration
import com.se.music.widget.RecommendItemView

/**
 * Created by gaojin on 2018/1/6.
 */
class NewSongExpressBlock : LinearLayout {

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
        View.inflate(context, R.layout.online_new_song_express_block, this)
        initView()
    }

    private fun initView() {
        iconEnter = findViewById(R.id.express_icon_enter)
        recommendRecycleView = findViewById(R.id.express_recycle_view)
        recommendRecycleView.layoutManager = GridLayoutManager(context, 3)

        val mDividerItemDecoration = GridItemDecoration(context, LinearLayoutManager.HORIZONTAL, 3)
        mDividerItemDecoration.setDrawable(ContextCompat.getDrawable(context!!, R.drawable.transparent_divider)!!)
        recommendRecycleView.addItemDecoration(mDividerItemDecoration)
        recommendRecycleView.isNestedScrollingEnabled = false
    }

    fun dataChanged(data: ExpressInfoModel) {
        recommendRecycleView.adapter = ExpressGridAdapter(context, data)
    }

    class ExpressGridAdapter(private var context: Context, data: ExpressInfoModel) : RecyclerView.Adapter<RecommendViewHolder>() {
        private val itemCount = 3
        private var mData: ExpressInfoModel = data
        override fun onBindViewHolder(holderRecommend: RecommendViewHolder, position: Int) {
            when (position) {
                0 -> {
                    val description = StringBuilder()
                    val songData = mData.new_album?.data?.song_list?.get(0)
                    songData?.singer?.forEach {
                        description.append(it.name)
                        description.append("&")
                    }
                    if (description.isNotEmpty()) {
                        description.replace(description.length - 1, description.length, "")
                        description.append(" - ")
                    }
                    description.append(songData?.name)

                    holderRecommend.recommendView.setImageView(String.format(picBaseUrl_150, songData?.album?.mid)).setDescription(description.toString())
                }
                1 -> {
                    val albumData = mData.new_song?.data?.album_list?.get(0)
                    val description = StringBuilder()
                    description.append(albumData?.album?.name)
                    if (description.isNotEmpty()) {
                        description.append(" - ")
                    }
                    albumData?.author?.forEach {
                        description.append(it.name)
                        description.append("&")
                    }

                    if (description.isNotEmpty()) {
                        description.replace(description.length - 1, description.length, "")
                    }
                    holderRecommend.recommendView.setImageView(String.format(picBaseUrl_150, albumData?.album?.mid)).setDescription(description.toString())
                }
                2 -> {
                    val albumData = mData.new_song?.data?.album_list?.get(1)
                    val description = StringBuilder()
                    description.append(albumData?.album?.name)
                    if (description.isNotEmpty()) {
                        description.append(" - ")
                    }
                    albumData?.author?.forEach {
                        description.append(it.name)
                        description.append("&")
                    }

                    if (description.isNotEmpty()) {
                        description.replace(description.length - 1, description.length, "")
                    }
                    holderRecommend.recommendView.setImageView(String.format(picBaseUrl_150, albumData?.album?.mid)).setDescription(description.toString())
                }
            }
        }

        override fun getItemCount(): Int {
            return itemCount
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
            return RecommendViewHolder(RecommendItemView(context))
        }
    }

    class RecommendViewHolder(itemView: RecommendItemView) : RecyclerView.ViewHolder(itemView) {
        var recommendView: RecommendItemView = itemView
    }
}