package com.se.music.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Nullable
import com.se.music.R
import com.se.music.utils.loadUrl

/**
 * Created by gaojin on 2017/12/31.
 */
class RecommendItemView : LinearLayout {
    private lateinit var itemHeader: AutoHeightImageView
    private lateinit var descriptionView: TextView

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    fun init() {
        orientation = LinearLayout.VERTICAL
        View.inflate(context, R.layout.online_recommend_item_view, this)
        itemHeader = findViewById(R.id.recommend_item_head)
        descriptionView = findViewById(R.id.song_list_description)
    }

    fun setImageView(@Nullable url: String): RecommendItemView {
        itemHeader.loadUrl(url)
        return this
    }

    fun setDescription(@Nullable description: String): RecommendItemView {
        descriptionView.text = description
        return this
    }
}