package com.se.music.mvvm

import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.se.music.R
import com.se.music.support.utils.inflate

/**
 *Author: gaojin
 *Time: 2020/10/10 4:15 PM
 */

class SingerCategoryAdapter : RecyclerView.Adapter<SingerCategoryAdapter.SingerCategoryViewHolder>() {

    private var selectPosition = 0

    private val data = mutableListOf<CategoryInfo>()
    private var itemClick: ((CategoryInfo) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingerCategoryViewHolder {
        return SingerCategoryViewHolder(parent.inflate(R.layout.singer_list_filter_item) as TextView)
    }

    override fun onBindViewHolder(holder: SingerCategoryViewHolder, position: Int) {
        data[position].let {
            holder.bind(position == selectPosition, it)
        }
    }

    override fun getItemCount() = data.size

    fun setData(list: List<CategoryInfo>) {
        data.addAll(list)
        notifyDataSetChanged()
    }

    fun updateSelectPosition(position: Int) {
        selectPosition = position
        notifyDataSetChanged()
    }


    fun setOnItemClickListener(listener: (CategoryInfo) -> Unit) {
        itemClick = listener
    }

    inner class SingerCategoryViewHolder(private val textView: TextView) : RecyclerView.ViewHolder(textView) {
        init {
            textView.setOnClickListener {
                val categoryInfo = data[absoluteAdapterPosition].apply {
                    index = absoluteAdapterPosition
                }
                itemClick?.invoke(categoryInfo)
            }
        }

        fun bind(isSelected: Boolean, category: CategoryInfo) {
            val textColor = if (isSelected) R.color.white else R.color.light_black
            textView.setTextColor(ContextCompat.getColor(textView.context, textColor))
            textView.isSelected = isSelected
            textView.text = category.name
        }
    }
}